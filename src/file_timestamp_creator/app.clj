(ns file-timestamp-creator.app
  (:require [clojure.string :as str])
  (:import (org.joda.time LocalDateTime)
           (java.io File))
  (:gen-class))

;; Current time
(def ^LocalDateTime curr-time (LocalDateTime.))

;; Custom date format
(def ^String custom-format "-yyyy-MM-dd_HH:mm:ss")

;; Regex dot
(def regex-dot #"\.")

;; Filename prefix
(def filename-prefix (. curr-time toString custom-format))

;; File or folder predicate
(defn file-or-dir? ^Boolean [^String arg-name] (empty? (re-seq regex-dot arg-name)))

;; Create file/folder name
(defn assemble-filename ^String [^String n] (if (file-or-dir? n)
                              (str n filename-prefix)
                                                 (let [parts (str/split n regex-dot)]
                                                   (str (first parts) filename-prefix "." (first (rest parts))))))

;; Creates file or folder
(defn create-file-or-dir [^String n] (if (file-or-dir? n)
                               (.mkdir (File. (assemble-filename n)))
                                                  (.createNewFile (File. (assemble-filename n)))))

(defn -main [& args] (dorun (map create-file-or-dir args)))
;;TODO: add unit tests
;;TODO: revise *.md files
