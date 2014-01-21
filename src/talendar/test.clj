(ns talendar.test
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]
           )
  )
(declare example movie)

(defn setup []


  (def movie (GSMovie. example "./data/station.mov"))
  (.play movie )
  (.goToBeginning movie)
  (.pause movie)
  )

(defn draw []


  (comment
    "this way works but i'll prefer to work with event handler"
    )
  (comment when (.available movie)
     (.read movie))
  (image movie 100 100)
  )

(defn mmmovieEvent [e]
  (println "listen movie event!")
  (.read movie)
  )
(def new-frame (atom 0))

(defn keyed []
  (when-not (.isSeeking movie)
      (condp = (raw-key)
        \q  (when (> @new-frame 0)
              (swap! new-frame dec))

        \w (when (< @new-frame  (.length movie))
             (swap! new-frame inc))
        "nothing")
      (.play movie)
      (.jump movie @new-frame)
      (.pause movie)w
      )

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [300 300]
  :target :perm-frame
  :key-pressed keyed
;  :mouse-pressed raton
;  :movie-event movieEvent
  )
