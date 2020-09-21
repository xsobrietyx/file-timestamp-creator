(ns ninja.hibernate.file.timestamp.creator.app-test
  (:require [clojure.test :refer :all]
            [ninja.hibernate.file.timestamp.creator.app :refer :all]))

(deftest file-or-dir?-test
  (testing "File case, should be evaluated to false."
    (is (= (file-or-dir? "hello-world.txt") false)))
  (testing "Folder case, should be evaluated to true."
    (is (= (file-or-dir? "Folder-name") true))))

(deftest first-dot?-test
  (testing "Testing whether supplied name starts with a dot."
    (is (= (first-dot? ".bashrc") true) "Should be true because of trailing dot.")
    (is (= (first-dot? "autoexec.bat") false) "Should be false.")))

(deftest last-dot?-test
  (testing "Testing whether supplied name ends with a dot."
    (is (= (last-dot? "somePicture.") true) "Should be true because name ends with dot.")
    (is (= (last-dot? "command.com") false) "Should be false.")))

(deftest valid-name?-test
  (testing "Generic case."
    (is (= (valid-name? ".zshrc") false) "Should be false because of trailing dot.")
    (is (= (valid-name? "picture.") false) "Should be false because of ending with dot.")
    (is (= (valid-name? "initial.ddl.tmp") false) "Should be false because of multiple dots.")
    (is (= (valid-name? "command.com") true) "Should be true.")
    (is (= (valid-name? "bin") true) "Should be true.")))

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
  (testing "Correct EOF."
    (is (= (count (re-seq #".pdf" (assemble-filename "report.pdf"))) 1)))
  (testing "Existence of created files."
    (def pref "test-resources-")
    (is (true? (create-file-or-dir (str pref "File.log")))
        "File/folder that was created will be deleted automatically after tests run.")
    (is (true? (create-file-or-dir (str pref "Folder")))
        "File/folder that was created will be deleted automatically after tests run.")
    (is (= (remove-files-by-name pref) "Removed files/folders with prefix test-resources- successfully."))))
