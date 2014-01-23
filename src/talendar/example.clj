(ns talendar.example
  (:use [quil.core]
        [talendar.helper]
        [talendar.toda :as toda])
(:import [codeanticode.gsvideo GSMovie])
    (:require [clojure.core.async :as async :refer [<! >! <!! timeout chan alt! go put!]])

  )
(def alerta (chan))
(def loading (chan))
(def movie-at (atom nil))
(def initiated (atom false))
(defn get-movie [] @movie-at)
(def new-frame (atom 0))
(declare example)



(defn setup []


  )
(def draw-printed (atom false))

(defn draw []
  (when-not @draw-printed
    (println "drawing! " @new-frame)
    (reset! draw-printed true)
    )
  (when @initiated
    (image (get-movie) 100 100)
    )
  #_(println  (get-movie))

  )


(defn movieEvent [movie]


  (reset! draw-printed false)

  (comment
    "before (.read movie) it's the old frame"
    (image movie 0 0))
  (println "listen movie event! frame" (.frame movie)  @new-frame)
  (.read movie)
  (put! loading (.frame movie))
    (comment
    "after (.read movie) it's the new frame"
    (image movie 180 180))




  )

(defn continue-to-frame []
  (.play (get-movie))
  (.jump (get-movie) @new-frame)
  (.pause (get-movie))
  )

(defn load-buffer-start-ready-movie []
  (.play (get-movie) )
  (.goToBeginning (get-movie))
  (.pause (get-movie))
  (put! alerta (.ready (get-movie)))
)

(defn keyed []
  (println "key:" (raw-key) (.isSeeking (get-movie)) @initiated)
  (if-not (.isSeeking (get-movie))
    (condp = (raw-key)
      \q  (when (> @new-frame 0)

            (swap! new-frame dec)
            (continue-to-frame)
            )
      \w (when (< @new-frame  (.length (get-movie)))
           (swap! new-frame inc)
           (continue-to-frame))
      \x (do (println "pdfing") (let [pdf (create-graphics 300 300 :pdf  "./hola.pdf")]
                                  (begin-record :pdf "hola.pdf")))
      \z (end-record)
      \o            (do
             (reset! new-frame 0)
             (load-buffer-start-ready-movie)

            )
      "nothing")
    (println "is seeking now!!")
    )

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [300 300]
  :target :perm-frame
  :key-pressed keyed
  :movie-event movieEvent
  )

(defn load-frame [the-frame]
  (reset! new-frame the-frame)
  (continue-to-frame)
  )


(go
 (while true
   (let [frame (<! loading)]
     (print "load-frame" frame)
     (load-frame (inc frame))))
 )




(reset! movie-at (GSMovie. example "./data/palmera.mov"))

 (go
  (println "movie loadedddddddd" (<! alerta))
  (reset! initiated true)
  (let [jan ((vec (toda/get-year-data 2014)) 0)
               limit (:days-on-month jan)]
           (vec (map #(int (map-range % 0 limit 0 (.length (get-movie) ) ))
                      (range 1 (inc limit))
                      ))
)
  )

(comment (def alerta (chan))



 (go
  (println "not ready")
  (while (complement (.ready (get-movie)))

    )
  (>! alerta "ready!")

  )

 (.ready (get-movie)))
