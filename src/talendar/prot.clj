(ns talendar.prot
  (:use [quil.core]
        [talendar.grid]
        [talendar.helper]

        [talendar.toda :as toda])
  (:require         [talendar.video-loader :as vl])
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])

  )

(declare raton setup draw)
(defsketch proti
  :title "proti"
  :setup setup
  :draw draw
  :size [300 300]
  :mouse-pressed raton

  )

(def jan (first (toda/get-year-data 2014)))


(declare vl-1)

(defn setup []
  (background 100)
  (frame-rate 20)
  (def vl-1 (vl/set-up proti "./data/station.mov"   (:days-on-month jan)))
  )




(defn draw []
  (stroke 255)
   (fill 100)
   (rect 0 0 100 100 )
   (if (vl/is-loading-movie? (:movie vl-1) (:frames vl-1) (:number vl-1))
     (do
       (fill 255)
       (text (str (frame-count)) 50 50 20))
     (image (@(:frames vl-1)  (int (random (count @(:frames vl-1)))))
          0 0 100 100)
     )



  )

(defn raton []
  (println "raton")



  )
