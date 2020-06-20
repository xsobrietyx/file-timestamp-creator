(ns file-timestamp-creator.app
  (:require [clojure.string :as str])
  (:import (org.joda.time LocalDateTime)
           (java.io File))
  (:gen-class))

(def regex-dot #"\.")

(defn file-or-dir? ^Boolean [^String arg-name]
  "File or folder predicate. Folders -> true, files -> false"
  (empty? (re-seq regex-dot arg-name)))

(defn assemble-filename ^String [^String name]
  "Generate file/folder name"
  (let [filename-prefix (let [curr-time (LocalDateTime.)
                              custom-format "-yyyy-MM-dd_HH:mm:ss"]
                          (. curr-time toString custom-format))]
    (if (file-or-dir? name)
      (str name filename-prefix)
      (let [parts (str/split name regex-dot)]
        (str (first parts) filename-prefix "." (first (rest parts)))))))

(defn create-file-or-dir [^String name]
  "Creates file or folder in current path"
  (if (file-or-dir? name)
    (.mkdir (File. (assemble-filename name)))
    (.createNewFile (File. (assemble-filename name)))))

(defn -main [& args] (dorun (map create-file-or-dir args)))
;;TODO: revise *.md files
