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

(defn assemble-filename ^String [^String name]
  "Generate file/folder name"
  (if (file-or-dir? name)
    (str name filename-prefix)
    (let [parts (str/split name regex-dot)]
      (str (first parts) filename-prefix "." (first (rest parts))))))

(defn create-file-or-dir [^String name]
  "Creates file or folder in current path"
  (if (file-or-dir? name)
    (.mkdir (File. (assemble-filename name)))
    (.createNewFile (File. (assemble-filename name)))))

(defn -main [& args] (dorun (map create-file-or-dir args)))
;;TODO: add unit tests
;;TODO: revise *.md files
