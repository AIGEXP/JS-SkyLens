/**
 * Precompiled [conventions.spring-dependencies.gradle.kts][Conventions_spring_dependencies_gradle] script plugin.
 *
 * @see Conventions_spring_dependencies_gradle
 */
public
class Conventions_springDependenciesPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Conventions_spring_dependencies_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
