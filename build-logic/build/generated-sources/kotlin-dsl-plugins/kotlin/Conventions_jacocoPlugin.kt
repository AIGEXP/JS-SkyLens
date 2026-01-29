/**
 * Precompiled [conventions.jacoco.gradle.kts][Conventions_jacoco_gradle] script plugin.
 *
 * @see Conventions_jacoco_gradle
 */
public
class Conventions_jacocoPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Conventions_jacoco_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
