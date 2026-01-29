/**
 * Precompiled [conventions.spring-java.gradle.kts][Conventions_spring_java_gradle] script plugin.
 *
 * @see Conventions_spring_java_gradle
 */
public
class Conventions_springJavaPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Conventions_spring_java_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
