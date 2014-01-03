(ns talendar.core
  (:use [quil.core]
        [talendar.toda :as toda])
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])
  )


(declare example movie)
(def  img (atom nil))
(defn setup []
  (let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]
    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image "/Users/juan_mini/load.png"))
        )))
  (comment (def movie (GSMovie. example "./data/station.mov"))
           (.loop movie ))

  )

(def size-w 300)
(def size-h 300)
(defn draw []
  (frame-rate 1)

  (ellipse 100 100 30 30)
  (fill 0)
  (rect 0 0 300 300)

  (stroke 250)
  (let [r-w (quot size-w 6)
        r-h (quot size-h 6)
        jan (first (toda/get-year-data 2014))]
    (no-fill)
    (doall (for [x (range 6)
                 y (range 6)]
             (do
               (let [xx (* x r-w) yy (* y r-h)]
                 (rect xx yy (+ xx r-w) (+ yy r-h))))
             ))



    )

  (comment
    (when-not (nil? @img)
                                        ;    (image @img 0 0)
      )
    (when (.available movie)
      (.read movie))
    (image movie 100 100))
  )

(defn movieEvent [e]
  (println "aaaa")
  (.read movie)
  )

(defn raton []
  (comment (let [fc (JFileChooser.)
         rv (.showOpenDialog fc example) ]
     (when (zero? rv)
       (let [file (.getSelectedFile fc)]
         (reset! img (load-image (.getPath file)))
         ))))
  (println example)

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [size-w size-h]
  :mouse-pressed raton
  :movie-event movieEvent
  )
