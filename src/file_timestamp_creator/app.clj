(ns file-timestamp-creator.app
  (:require [clojure.string :as str])
  (:import (org.joda.time LocalDateTime)
           (java.io File))
  (:gen-class))

(def ^LocalDateTime curr-time (LocalDateTime.))

(def ^String custom-format "-yyyy-MM-dd_HH:mm:ss")

(def regex-dot #"\.")

(def filename-prefix (. curr-time toString custom-format))

(defn file-or-dir? ^Boolean [^String arg-name]
  "File or folder predicate. Folders -> true, files -> false"
  (empty? (re-seq regex-dot arg-name)))

(defn assemble-filename ^String [^String n]
  "Generate file/folder name"
  (if (file-or-dir? n)
    (str n filename-prefix)
    (let [parts (str/split n regex-dot)]
      (str (first parts) filename-prefix "." (first (rest parts))))))

(defn create-file-or-dir [^String n]
  "Creates file or folder in current path"
  (if (file-or-dir? n)
    (.mkdir (File. (assemble-filename n)))
    (.createNewFile (File. (assemble-filename n)))))

(defn -main [& args] (dorun (map create-file-or-dir args)))
;;TODO: add unit tests
;;TODO: revise *.md files
