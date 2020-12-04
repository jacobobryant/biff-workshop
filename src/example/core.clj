(ns example.core
  (:require
    [biff.core :as biff]
    [biff.project :as project]
    [example.email :refer [send-email]]
    [example.handlers :refer [api]]
    [example.jobs :refer [jobs]]
    [example.routes :refer [routes]]
    [example.rules :refer [rules]]
    [example.static :refer [pages]]
    [example.triggers :refer [triggers]]))

; This is your app's main entry point. When you reach the point where you
; need more flexibility than what Biff provides out of the box, replace
; biff/default-spa-components with your own collection of components.
; See https://github.com/jacobobryant/biff/blob/master/src/biff/core.clj
; and https://github.com/jacobobryant/biff/blob/master/src/biff/project.clj.
(defn start [first-start]
  (let [sys (biff/start-system
              #:biff{:first-start first-start
                     :routes routes
                     :static-pages pages
                     :event-handler #(api % (:?data %))
                     :rules #'rules
                     :triggers #'triggers
                     :jobs jobs
                     :send-email #'send-email
                     :after-refresh `after-refresh}
              biff/default-spa-components)]
    (when (:biff/dev sys)
      ; This function lets Biff manage non-Clojure files for you (e.g.
      ; all-tasks/10-biff, and the contents of infra/).
      (project/update-spa-files sys))
    (println "System started.")))

(defn -main []
  (start true))

(defn after-refresh []
  (start false))

(comment
  ; Useful REPL commands:
  (biff.core/refresh)
  (->> @biff.core/system keys sort (run! prn))
  )
