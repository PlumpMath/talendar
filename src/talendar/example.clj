(ns talendar.example
  (:use quil.core)
  (:import [codeanticode.gsvideo GSMovie])
  )

(def movie-at (atom nil))
(defn get-movie [] @movie-at)
(declare example)



(defn setup []


  )
(def draw-printed (atom false))

(defn draw []
  (when-not @draw-printed
    (println "drawing! " (.frame (get-movie)))
    (reset! draw-printed true)
    )
  (when-not (nil? (get-movie))
    (image (get-movie) 100 100)
    )

  )
(def new-frame (atom 0))
; this method is hard coded on quil/applet.clj
(defn movieEvent [movie]
  (reset! draw-printed false)
  (println "listen movie event! frame" (.frame movie)  @new-frame)
  (.read movie)
  )



(defn keyed []
  (when-not (.isSeeking (get-movie))
      (condp = (raw-key)
        \q  (when (> @new-frame 0)

              (swap! new-frame dec))
        \w (when (< @new-frame  (.length (get-movie)))
             (swap! new-frame inc))
        \x (do (println "pdfing") (let [pdf (create-graphics 300 300 :pdf  "./hola.pdf")]
                                    (begin-record :pdf "hola.pdf")))
        \z (end-record)
        \o (do
               (swap! movie-at (GSMovie. example "./data/station.mov"))
  (.play (get-movie) )
  (.goToBeginning (get-movie))
  (.pause (get-movie))
)
        "nothing")
      (.play (get-movie))
      (.jump (get-movie) @new-frame)
      (.pause (get-movie))
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
  :movie-event movieEvent
  )
