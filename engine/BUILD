load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_test")

# Engine library: Contains core domain models and simulation schedulers
java_library(
    name = "engine_lib",
    srcs = glob(
        ["src/main/java/com/concurrencylabs/*.java"],
        exclude = ["src/main/java/com/concurrencylabs/Server.java"],
    ),
    visibility = ["//visibility:public"],
)

# Application entry point/web server
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

# Smoke unit test target
java_test(
    name = "engine_smoke_test",
    size = "small",
    srcs = ["src/test/java/com/concurrencylabs/EngineSmokeTest.java"],
    test_class = "com.concurrencylabs.EngineSmokeTest",
    deps = [
        ":engine_lib",
        "@junit_maven//:junit_junit",
    ],
)