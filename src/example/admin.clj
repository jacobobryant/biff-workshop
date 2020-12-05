(ns example.admin
  (:require
    [biff.core]
    [clojure.pprint :refer [pprint]]
    [crux.api :as crux]))


(comment

  (pprint
    (let [{:keys [biff/node]} @biff.core/system
          db (crux/db node)]
      (crux/q db
        {:find '[doc]
         :full-results? true
         :where '[[doc :sender]]})
      ))
  )
