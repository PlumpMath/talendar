(ns talendar.simple
  (:use quil.core)

  )
(declare example )

(defn setup []





  )

(defn draw []
  (fill (random 200))
  (rect 100 100 100 100)


  )





(defsketch example
  :title "jii"
  :setup setup
  :draw draw
  :size [300 300]
 ; :mouse-pressed raton
;  :movie-event movieEvent
  )
