(ns talendar.core
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]
           )
  )
(declare example movie)

(defn setup []


  (def movie (GSMovie. example "./data/station.mov"))
  (.loop movie )

  )

(defn draw []


  (comment
    "this way works but i'll prefer to work with event handler"
    (when (.available movie)
     (.read movie)))
  (image movie 100 100)
  )

(defn movieEvent [e]
  (println "listen movie event!")
  (.read movie)
  )



(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [300 300]
  :mouse-pressed raton
  :movie-event movieEvent
  )
