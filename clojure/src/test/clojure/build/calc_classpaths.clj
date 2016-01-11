;; Copyright 2014-2016 Red Hat, Inc, and individual contributors.
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns build.calc_classpaths
  (:require [leiningen.core.classpath :as cp]
            [leiningen.core.project :as project]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(let [[app-dir dest-dir & extra-entries] *command-line-args*]
  (println "Calculating lein classpath for" app-dir)
  (spit
    (io/file
      (doto (io/file dest-dir)
        (.mkdirs))
      "lein-classpath")
    (str/join ":"
      (-> app-dir
        (str "/project.clj")
        project/read
        cp/get-classpath
        (concat (map #(-> % io/file .getAbsolutePath)
                  extra-entries))))))
