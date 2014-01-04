(ns talendar.core
  (:use [quil.core]
        [talendar.grid]
        [talendar.toda :as toda])
  (:require         [talendar.video-loader :as vl])
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])
;  (:require [clojure.core.async :as async :refer [<! >! <!! timeout chan alt! go]])
  )


(declare example vl-1)
(def  img (atom nil))
(def click (atom [-1 -1]))

(def jan (first (toda/get-year-data 2014)))

(defn setup []

  (comment let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]
    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image "/Users/juan_mini/load.png"))
        )))

  (def vl-1 (vl/set-up example "./data/bubble.mov"   (:days-on-month jan)))
  (println (:number vl-1))

  )

(defn text-day [n x y]
      (text-size 20)
  (push-style)
  (fill 255 150)
  (text (str n) x y)
  (pop-style)
  )


(defn draw []

  (frame-rate 5)
  (ellipse 100 100 30 30)
  (fill 0)
  (rect 0 0 300 300)
;  (stroke 250)
  (no-stroke)
  (if (vl/is-loading-movie? (:movie vl-1) (:frames vl-1) (:number vl-1))
      (println "loading")

      (let [limit-grid (:days-on-month jan)]
        (doall
         (->> data-36-days
              (map (fn [[x y]]
                     (let [current-rect (coords x y)
                           rect-number (get-rect-number x y)
                           rect-number-clicked  (dec (get-rect-number @click))
                           cal-day (- rect-number rect-number-clicked)
                           possible-day? (and (> cal-day 0) (<= cal-day limit-grid))
                           ]
                       (println cal-day)
                       (if (>= 36 (+ rect-number-clicked limit-grid))
                         (if possible-day?
                           (do               ;selected
                             (fill 155)
                             (paint current-rect)
                             (if (is-sunday? cal-day 1 2014)
                               (tint 255 10 10  )
                               (tint 255)
                               )

                             ( image (@(:frames vl-1)  (dec cal-day)) (current-rect :xx) (current-rect :yy) r-w r-h)

                             )
                           (do
                             (fill 255)
                             (paint current-rect)
                             ))
                         (do
                           (fill 255)
                           (paint current-rect))
                         )

                       (when possible-day?
                         (text-day cal-day (+ (:xx current-rect) (/ r-w 2)) (+  (:yy current-rect) (/ r-h 2))))

                       ) ) )))))
(comment if (vl/is-loading-movie? (:movie vl-1) (:frames vl-1) (:number vl-1))
     (do
       (fill 255)
       (text (str (frame-count)) 50 50 20)))



)
(defn get-selected-rect [[x y]]
  "returns a vector indexed in 0 [0 0] indicating col and row"
  [ (dec (first (filter #(and (>= (* % r-w) x) (<=  (* (dec %) r-w) x)) (range 1 7))))
    (dec (first  (filter #(and (>= (* % r-h) y) (<=  (* (dec %) r-h) y)) (range 1 7))))]
  )

(defn raton []
  (reset! click (get-selected-rect [(mouse-x) (mouse-y)]))

  (comment (let [fc (JFileChooser.)
         rv (.showOpenDialog fc example) ]
     (when (zero? rv)
       (let [file (.getSelectedFile fc)]
         (reset! img (load-image (.getPath file)))
         ))))
  (println @click)

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [size-w size-h]
  :mouse-pressed raton

  )
