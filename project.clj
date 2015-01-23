(defproject
  stand-down
  "0.1.0-SNAPSHOT"
  :description
  "FIXME: write description"
  :url
  "http://example.com/FIXME"
  :dependencies
  [[org.clojure/clojurescript "0.0-2644"]
   [prone "0.8.0"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]
   [selmer "0.7.7"]
   [com.taoensso/tower "3.0.2"]
   [markdown-clj "0.9.58" :exclusions [com.keminglabs/cljx]]
   [im.chit/cronj "1.4.3"]
   [com.taoensso/timbre "3.3.1"]
   [org.postgresql/postgresql "9.3-1102-jdbc41"]
   [noir-exception "0.2.3"]
   [korma "0.4.0"]
   [cljs-ajax "0.3.4"]
   [lib-noir "0.9.5"]
   [org.clojure/clojure "1.6.0"]
   [environ "1.0.0"]
   [ring-server "0.3.1"]
   [reagent-forms "0.2.9"]
   [secretary "1.2.1"]
   [ragtime "0.3.6"]]
  :repl-options
  {:init-ns stand-down.repl}
  :jvm-opts
  ["-server"]
  :plugins
  [[lein-ring "0.9.0"]
   [lein-environ "1.0.0"]
   [lein-ancient "0.5.5"]
   [lein-cljsbuild "1.0.4"]
   [ragtime/ragtime.lein "0.3.6"]]
  :ring
  {:handler stand-down.handler/app,
   :init stand-down.handler/init,
   :destroy stand-down.handler/destroy,
   :uberwar-name "stand-down.war"}
  :profiles
  {:uberjar
   {:cljsbuild
    {:jar true,
     :builds
     {:app
      {:source-paths ["env/prod/cljs"],
       :compiler {:optimizations :advanced, :pretty-print false}}}},
    :hooks [leiningen.cljsbuild],
    :omit-source true,
    :env {:production true},
    :aot :all},
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]}}},
    :dependencies
    [[ring-mock "0.1.5"]
     [ring/ring-devel "1.3.2"]
     [pjstadig/humane-test-output "0.6.0"]],
    :injections
    [(require 'pjstadig.humane-test-output)
     (pjstadig.humane-test-output/activate!)],
    :env {:dev true}}}
  :ragtime
  {:migrations ragtime.sql.files/migrations,
   :database
   "jdbc:postgresql://localhost/stand_down?user=db_user_name_here&password=db_user_password_here"}
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src-cljs"],
     :compiler
     {:output-dir "resources/public/js/out",
      :externs ["react/externs/react.js"],
      :optimizations :none,
      :output-to "resources/public/js/app.js",
      :source-map "resources/public/js/out.js.map",
      :pretty-print true}}}}
  :uberjar-name
  "stand-down.jar"
  :min-lein-version "2.0.0")