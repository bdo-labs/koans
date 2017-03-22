(defproject koans "0.0.1"
  :description "Clojure(Script) for dummies"

  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.473"]
                 [org.clojure/test.check "0.9.0"]
                 [cljsjs/react-highlight "1.0.5-0"]
                 [nightlight "1.6.3"]
                 [re-frame "0.9.2"]
                 [re-frisk "0.3.2"]
                 [secretary "1.2.3"]
                 [ns-tracker "0.3.1"]
                 [reagent "0.6.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :source-paths ["src/clj" "src/cljs"]

  ;; These paths will be removed by running `lein clean`
  :clean-targets ^{:protect false} ["target"
                                    "resources/public/css"
                                    "resources/public/js/compiled"]

  :figwheel {:css-dirs    ["resources/public/css"]}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.1"]
                   [figwheel-sidecar "0.5.9"]
                   [com.cemerick/piggieback "0.2.1"]]

    :plugins [[lein-figwheel "0.5.9"]
              [lein-doo "0.1.7"]
              ;; `lein nightlight --port 4000  --url "http://localhost:3000` to start an editor on the port 4000
              [nightlight/lein-nightlight "1.6.3"]]}}

  :cljsbuild
  {:builds

   ;; `lein figwheel dev` for pretty-printing, source-maps and code hot-loading
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "koans.core/mount-root"}
     :compiler     {:main                 koans.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    ;; `lein cljsbuild min` for a production-build with dead-code removal and minification
    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            koans.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ;; `lein test once` will build without optimizations and run all the tests specified in the runner
    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          koans.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}]})
