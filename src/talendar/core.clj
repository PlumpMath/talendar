(ns talendar.core
  (:use [quil.core]
        [talendar.grid]
        [talendar.toda :as toda]
        [talendar.videoloader :as vl])

  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])
;  (:require [clojure.core.async :as async :refer [<! >! <!! timeout chan alt! go]])
  )



(declare  vl-1 setup raton key-press draw)
(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [size-w size-h]
  :mouse-pressed raton
  :key-typed key-press
  )
(def  img (atom nil))
(def click (atom [-1 -1]))
(def mm 5)
(def jan ((vec (toda/get-year-data 2014)) (dec mm)) )

(defn setup []

  (comment let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]
    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image "/Users/juan_mini/load.png"))
        )))

  (def vl-1 (vl/set-up example "/Users/juanantonioruz/Desktop/hoy/misPelis/giro en bici.MOV"   (:days-on-month jan)))
  (println (:number vl-1))

  )

(defn text-day
  ([n x y c]
     (text-size 10)
     (push-style)
     (fill c)
     (text (str n) x y)
     (pop-style))
  ([n x y]
     (text-day n x y 0)
     )
  )


(defn draw []
  (while (vl/is-loading-movie? (:movie vl-1) (:frames vl-1) (:number vl-1))

    (println "loadding frames"))
  (frame-rate 15)
  (ellipse 100 100 30 30)
  (fill 0)
  (rect 0 0 300 300)
;  (stroke 250)
  (no-stroke)
  (if (not= (count @(:frames vl-1)) (count (:number vl-1)))

      (println "loading")
      (let [limit-grid (:days-on-month jan)]
        (dorun (map (fn [[x y]]
                (let [current-rect (coords x y)
                      rect-number (get-rect-number x y)
                      rect-number-clicked  (dec (get-rect-number @click))
                      cal-day (- rect-number rect-number-clicked)
                      possible-day? (and (> cal-day 0) (<= cal-day limit-grid))
                      ]
                  (if (>= 36 (+ rect-number-clicked limit-grid))
                    (if possible-day?
                      (do               ;selected
                        (if (is-sunday? cal-day 1 2014)
                          (fill 255)
                          (fill 255)
                          )
                           (paint current-rect)
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

                    #_(if (is-sunday? cal-day 1 2014)
                      (tint 255 150 0)
                      (tint 255))
                    (image (:img ((vec (sort-by :nframe @(:frames vl-1))) (dec cal-day))) (current-rect :xx) (current-rect :yy) r-w r-h)


                    (text-day cal-day (+ (:xx current-rect) (/ r-w 2)) (+  (:yy current-rect) (- r-h 5)))
                    (text-day cal-day (+ (:xx current-rect) (dec (/ r-w 2))) (+  (:yy current-rect) (- r-h 6)) 200) )

                  ) ) data-36-days )))))
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
(defn key-press []
  (println (str "Key pressed: " (raw-key)))
  (save "jolin3.jpg")
)
