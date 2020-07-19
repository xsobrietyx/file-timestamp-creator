(ns ninja.hibernate.file.timestamp.creator.app
  (:require [clojure.string :as str])
  (:import (org.joda.time LocalDateTime)
           (java.io File FilenameFilter))
  (:gen-class))

(def regex-dot #"\.")

(defn file-or-dir? ^Boolean [^String name]
  "File or folder predicate. Folders -> true, files -> false."
  (empty? (re-seq regex-dot name)))

(defn first-dot? ^Boolean [^String name]
  "Function to check for trailing dot."
  (= (first (for [[idx elt] (map-indexed vector name) :when (#{\.} elt)] idx)) 0))

(defn valid-name? ^Boolean [^String name]
  "Validates whether file name entered has only one dot or less."
  (and (not (first-dot? name)) (<= (count (re-seq regex-dot name)) 1)))

(defn assemble-filename ^String [^String name]
  "Generate file/folder name."
  (let [filename-prefix (let [curr-time (LocalDateTime.)
                              custom-format "-yyyy-MM-dd_HH:mm:ss"]
                          (. curr-time toString custom-format))]
    (if (valid-name? name)
      (if (file-or-dir? name)
        (str name filename-prefix)
        (let [parts (str/split name regex-dot)]
          (str (first parts) filename-prefix "." (first (rest parts)))))
      (throw (IllegalArgumentException.
               "IllegalArgumentException: ")))))

(def exception-msg
  "File name with multiple/trailing dot(s) is not allowed.
  Please enter a valid file/folder name. Wrong name: ")

(defn create-file-or-dir [^String name]
  "Creates file or folder in current path."
  (let [new-name (try (assemble-filename name)
                      (catch IllegalArgumentException e (println (str (.getMessage e) exception-msg name))))]
    (if (not (nil? new-name))
      (if (file-or-dir? name)
        (.mkdir (File. ^String new-name))
        (.createNewFile (File. ^String new-name)))
      identity
      )))

(defn remove-files-by-name [prefix]
  "Removes files/folders by prefix supplied. Created only for test usage purposes."
  (let
    [filter (fn [prefix]
              (reify FilenameFilter
                (accept [_ _ name]
                  (.startsWith name prefix))
                ))]
    (loop [files-to-remove (seq (.list (File. ".") (filter prefix)))]
      (if (nil? (first files-to-remove))
        (format "Removed files/folders with prefix %s successfully." prefix)
        (do
          (.delete (File. ^String (first files-to-remove)))
          (recur (rest files-to-remove)))
        ))
    ))

(def help-message "usage:
<alias> [<fileNames/dirNames>]
Additional commands and options will be added soon.")

(defn print-help [] (println help-message))

(defmulti arguments-check
          "Checking whether we should show help message or proceed with main application flow."
          (fn [args] (empty? args)))
(defmethod arguments-check true [_] (print-help))
(defmethod arguments-check false [args] (dorun (map create-file-or-dir args)))

(defn -main [& args] (arguments-check args))
;;TODO: add specs-alpha specs for data checks and generative testing