(ns talendar.core
  (:use [quil.core]
        [talendar.grid]
        [talendar.toda :as toda])
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])
  )


(declare example movie)
(def  img (atom nil))
(def click (atom [-1 -1]))
(defn setup []
    (text-size 10)
  (let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]
    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image "/Users/juan_mini/load.png"))
        )))
  (comment (def movie (GSMovie. example "./data/station.mov"))
           (.loop movie ))

  )

(def jan (first (toda/get-year-data 2014)))

(defn draw []
  (frame-rate 20)
  (ellipse 100 100 30 30)
  (fill 0)
  (rect 0 0 300 300)
  (stroke 250)
  (doall (map (fn [[x y]]
                (let [current-rect (coords x y)
                      n-day (get-rect-number x y)
                      click-day (dec (get-rect-number @click))
                      cal-day (- n-day click-day)
                      possible-day? (and (> cal-day 0) (<= cal-day (:days-on-month jan)))
                      ]
                  (if (> 36 (+ click-day (:days-on-month jan)))
                    (if possible-day?
                      (fill 155) ; selected!
                      (fill 80))
                    (fill 0)
                      )
                  (paint current-rect)
                  (when possible-day?
                    (text-day cal-day (+ (:xx current-rect) (/ r-w 2)) (+  (:yy current-rect) (/ r-h 2))))
                  ) ) data-36-days))

  (comment
    (when-not (nil? @img)
                                        ;    (image @img 0 0)
      )
    (when (.available movie)
      (.read movie))
    (image movie 100 100)))

(defn text-day [n x y]
  (push-style)
  (fill 255 0 0)
  (text (str n) x y)
  (pop-style)
  )

(defn movieEvent [e]
  (println "aaaa")
  (.read movie)
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
  :movie-event movieEvent
  )
