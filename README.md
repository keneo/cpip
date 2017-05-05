# Cpip

## Requirements

- [Java SE](http://www.oracle.com/technetwork/java/javase/overview)
- [Gradle](http://www.gradle.org)

## Usage

```
C:\Users\Bartek\dev\cpip>gradle run
Starting a Gradle Daemon (subsequent builds will be faster)
:compileJava
C:\Users\Bartek\dev\cpip\src\main\java\Node.java:1: warning: Bool is internal proprietary API and may be removed in a future release
import com.sun.org.apache.xpath.internal.operations.Bool;
                                                   ^
1 warning
:compileGroovy NO-SOURCE
:processResources NO-SOURCE
:classes
:run
Fri May 05 17:58:00 CEST 2017 Running 1000000 random geo lookups...
Fri May 05 17:58:00 CEST 2017 At point: 82.98715090120953,-74.11348625827934: Antarctica
Fri May 05 17:58:00 CEST 2017 At point: -3.9943620534111233,-6.869301367763342: international
Fri May 05 17:58:00 CEST 2017 At point: -18.504579157807427,35.58822178041538: international
Fri May 05 17:58:00 CEST 2017 At point: -80.00376992718861,46.79294900648338: Canada
Fri May 05 17:58:00 CEST 2017 At point: -101.07197930704037,75.67452209704132: Canada
Fri May 05 17:58:00 CEST 2017 At point: 138.98008338369982,-50.03971161748396: international
Fri May 05 17:58:00 CEST 2017 At point: 138.19628132511605,-10.896194323624897: international
Fri May 05 17:58:00 CEST 2017 At point: -38.56448117817047,89.0517604305549: international
Fri May 05 17:58:00 CEST 2017 At point: 137.34028744675686,-39.033190911177144: international
Fri May 05 17:58:00 CEST 2017 At point: -90.78286675131956,16.841570259842626: Guatemala
Fri May 05 17:58:00 CEST 2017 ...
Fri May 05 17:58:17 CEST 2017 Finished

BUILD SUCCESSFUL

Total time: 24.067 secs
C:\Users\Bartek\dev\cpip>
```



---

Copyright &copy; 2017 Bartek.
