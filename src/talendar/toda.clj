(ns talendar.toda
   (:refer-clojure :exclude [extend])
   (:use [clj-time.core :exclude (second)]
         [clj-time.predicates :only (sunday?)])
    )

(defn get-year-data [year] (let [ms (map #(local-date year % 1) (range 1 13))]

   (let [days-of-week-first-day-month (map day-of-week ms)
         last-days-month (map #(minus (plus % (months 1)) (days 1)) ms)]
     (map
      (fn [m last-day day-week]
        (let [days-on-month (day last-day)
              ]
          {:month m :first-day-week day-week :days-on-month days-on-month })
        ) (range 1 13) last-days-month days-of-week-first-day-month )
     )

   ))

(defn is-sunday? [-day -month -year]
  (sunday? (local-date -year -month -day)))
