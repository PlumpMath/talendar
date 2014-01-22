(ns talendar.test
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]

           )
  )
(declare example movie)



(defn setup []


  (def movie (GSMovie. example "./data/palmera.mov"))
  (.play movie )
  (.goToBeginning movie)
  (.pause movie)
  )

(defn draw []




  (image movie 100 100)
  )

; this method is hard coded on quil/applet.clj
(defn movieEvent [movie]
  (println "listen movie event!" movie)
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
        \x (do (println "pdfing") (let [pdf (create-graphics 300 300 :pdf  "./hola.pdf")
                                        ]
                                    (begin-record :pdf "hola.pdf")
                                    ))
        \z (end-record)

        "nothing")
      (.play movie)
      (.jump movie @new-frame)
      (.pause movie)
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
