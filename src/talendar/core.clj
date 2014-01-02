(ns talendar.core
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]
           )
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
  (def movie (GSMovie. example "./data/station.mov"))
  (.loop movie )

  )

(defn draw []
  (image movie 100 100)
  (stroke 0)
  (ellipse 100 100 30 30)
  (when-not (nil? @img)
   ; (image @img 0 0)
    )
  (when (.available movie)
    (.read movie))

  )

(defn movieEvent [e]
  (println "aaaa")
  (.read movie)
  )

(defn raton []


  (let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]

    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image (.getPath file)))
        )))
  (println example)

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [300 300]
  :mouse-pressed raton
  :movie-event movieEvent
  )
