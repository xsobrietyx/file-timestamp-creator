(ns file-timestamp-creator.app-test
  (:require [clojure.test :refer :all]
            [file-timestamp-creator.app :refer :all]))

(deftest file-or-dir?-test
  (testing "File case, should be evaluated to false."
    (is (= (file-or-dir? "hello-world.txt") false)))
  (testing "Folder case, should be evaluated to true."
    (is (= (file-or-dir? "Folder-name") true))))

(deftest assemble-filename-test
  (testing "Generic case, should be evaluated to name that contains initial name."
    (is (clojure.string/includes? (assemble-filename "Some-folder-name") "Some-folder-name")))
  (testing "New name should be longer that initial by 20 chars."
    (is (> (count (assemble-filename "hello-world.txt")) (count "hello-world.txt")))
    (is (= (- (count (assemble-filename "hello-world.txt")) (count "hello-world.txt")) 20)))
  (testing "Timestamp by regexp, file."
    (is (= (count (re-seq #"-(\d){4}-(\d){2}-(\d){2}_(\d){2}:(\d){2}:(\d){2}" (assemble-filename "good-job.clj"))) 1)))
  (testing "Timestamp by regexp, folder."
    (is (= (count (re-seq #"-(\d){4}-(\d){2}-(\d){2}_(\d){2}:(\d){2}:(\d){2}" (assemble-filename "Temporary"))) 1)))
  (testing "Correct EOF of file."
    (is (= (count (re-seq #".pdf" (assemble-filename "report.pdf"))) 1)))
  (testing "Existence of created files"
    (is (true? (create-file-or-dir "test.log"))
        "File/folder that was created should be deleted manually after.")
    (is (true? (create-file-or-dir "ResourcesFolder"))
        "File/folder that was created should be deleted manually after.")))
