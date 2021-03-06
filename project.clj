(defproject file-timestamp-creator "0.1.2-SNAPSHOT"
  :description "File creation helper app"
  :url "https://hibernate.ninja"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; https://mvnrepository.com/artifact/joda-time/joda-time
                 [joda-time/joda-time "2.10.6"]]
  :main ninja.hibernate.file.timestamp.creator.app
  :aot [ninja.hibernate.file.timestamp.creator.app]
  :repl-options {:init-ns ninja.hibernate.file.timestamp.creator.app}
  :uberjar-name "file-creator-stadalone-%s.jar"
  :target-path "target/%s/")
