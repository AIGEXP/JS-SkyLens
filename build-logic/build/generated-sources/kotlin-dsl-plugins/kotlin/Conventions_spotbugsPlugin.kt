/**
 * Precompiled [conventions.spotbugs.gradle.kts][Conventions_spotbugs_gradle] script plugin.
 *
 * @see Conventions_spotbugs_gradle
 */
public
class Conventions_spotbugsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Conventions_spotbugs_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
