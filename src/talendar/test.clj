(ns talendar.test
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]

           )
  )
(declare example )

(def movie (atom nil))

(defn get-movie []
  @movie
  )

(defn setup []

  (reset! movie (GSMovie. example "./data/station.mov"))



  )

(defn draw []



  (when-not (nil? @movie)
    (image (get-movie) 100 100))
  )

; this method is hard coded on quil/applet.clj
(defn movieEvent [movie]
  (println "listen (movie) event!" movie)
  (.read movie)
  )

(def new-frame (atom 0))

(defn continue-new-frame []
        (.play (get-movie))
      (.jump (get-movie) @new-frame)
      (.pause (get-movie))
)

(defn keyed []
  (when-not (.isSeeking (get-movie))
      (condp = (raw-key)
        \q  (when (> @new-frame 0)
              (swap! new-frame dec)
              (continue-new-frame))
        \w (when (< @new-frame  (.length (get-movie)))
             (swap! new-frame inc)
             (continue-new-frame))
        \x (do (println "pdfing") (let [pdf (create-graphics 300 300 :pdf  "./hola.pdf")
                                        ]
                                    (begin-record :pdf "hola.pdf")
                                    ))
        \o (do
               (.play (get-movie) )
               (.goToBeginning (get-movie))
               (.pause (get-movie)))
        \z (end-record)

        "nothing")
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
