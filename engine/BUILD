load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_library(
    name = "engine_lib",
    srcs = ["src/main/java/com/concurrencylabs/Simulator.java"],
    visibility = ["//visibility:public"],
)

java_binary(
    name = "server",
    srcs = ["src/main/java/com/concurrencylabs/Server.java"],
    main_class = "com.concurrencylabs.Server",
    data = ["//ui:bundle"],
    deps = [
        "@bazel_tools//tools/java/runfiles",
        ":engine_lib",
    ],
    visibility = ["//visibility:public"],
)