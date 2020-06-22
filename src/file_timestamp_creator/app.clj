(ns file-timestamp-creator.app
  (:require [clojure.string :as str])
  (:import (org.joda.time LocalDateTime)
           (java.io File))
  (:gen-class))

(def regex-dot #"\.")

(defn file-or-dir? ^Boolean [^String arg-name]
  "File or folder predicate. Folders -> true, files -> false"
  (empty? (re-seq regex-dot arg-name)))

(defn first-dot? ^Boolean [^String arg-name]
  (= (first (for [[idx elt] (map-indexed vector arg-name) :when (#{\.} elt)] idx)) 0))

(defn valid-name? ^Boolean [^String arg-name]
  "Validates whether file name entered has only one dot or less."
  (and (not (first-dot? arg-name)) (<= (count (re-seq regex-dot arg-name)) 1)))

(defn assemble-filename ^String [^String name]
  "Generate file/folder name"
  (let [filename-prefix (let [curr-time (LocalDateTime.)
                              custom-format "-yyyy-MM-dd_HH:mm:ss"]
                          (. curr-time toString custom-format))]

    (if (valid-name? name)
      (if (file-or-dir? name)
        (str name filename-prefix)
        (let [parts (str/split name regex-dot)]
          (str (first parts) filename-prefix "." (first (rest parts)))))
      (throw (IllegalArgumentException.
               "File names with more than one dot is not allowed. Please enter a valid file/folder name.")))))

(defn create-file-or-dir [^String name]
  "Creates file or folder in current path"
  (let [new-name (try (assemble-filename name)
                      (catch IllegalArgumentException e (println (.getMessage e))))]
    (if (not (nil? new-name))
      (if (file-or-dir? name)
        (.mkdir (File. ^String new-name))
        (.createNewFile (File. ^String new-name)))
      identity
      )))

(defn -main [& args] (dorun (map create-file-or-dir args)))
;;TODO: revise *.md files
