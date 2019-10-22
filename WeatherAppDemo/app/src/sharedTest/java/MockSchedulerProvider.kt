import bachvu.weatherappdemo.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

class MockSchedulerProvider(private val testScheduler: TestScheduler? = null) : SchedulerProvider {

    override fun io(): Scheduler = testScheduler ?: Schedulers.trampoline()

    override fun mainThread(): Scheduler = testScheduler ?: Schedulers.trampoline()
}

