(ns talendar.helper
  (:use [quil.core]
        [talendar.helper])
  (:import [codeanticode.gsvideo GSMovie])

  )

(defn clone-image [im]
  (.loadPixels im)

  (let [myimage (create-image (. im width) (. im height) 1)]
    (set!   (. myimage pixels) (. im pixels))
    (.updatePixels myimage)
    (image myimage 0 0 300 300)
    myimage
    )


  )
