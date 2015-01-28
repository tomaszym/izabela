(ns cirkulerilo
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all])
  (:import [org.tejo.stashek.cirkulerilo.facts ListFact BoardFact CardFact ChecklistFact]))

(defrule call-for-contributions
  "When there are conditions sends out the emails"
  [ListFact (= name "Aktuala")
            (= ?listId id)]

  [CardFact (= name "Agordoj")
            (= ?agrodojCardId id)]

  [ChecklistFact (= idCard ?agordojCardId)
;                 (= "Dissendi alvokon @tomaszszymula" (.name (.head checkItems)))
                 (= "complete" (.state (.head checkItems)))
;                 (= "Alvoko dissendita @stashek" (.name (.head (.tail checkItems))))]
                 (= "incomplete" (.state (.head (.tail checkItems))))]
  =>
  (println ?agordojCardId)
)

;                 (not (.isCompleted ?secondCheckItem))
;                 (.isCompleted ?firstCheckItem)