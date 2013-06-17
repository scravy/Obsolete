package net.scravy.technetium.misses_o;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import net.scravy.persistence.EntityManager;
import net.scravy.persistence.EntityManagerWrapper;
import net.scravy.technetium.domain.Building;
import net.scravy.technetium.domain.Course;
import net.scravy.technetium.domain.CourseUnit;
import net.scravy.technetium.domain.Person;
import net.scravy.technetium.domain.Program;
import net.scravy.technetium.domain.Room;
import net.scravy.technetium.domain.TimeSlot;
import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.ValueUtil;

/**
 * The actual Scheduler.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("unused")
public class Scheduler {

	private final ArrayList<Course>[] table1;
	private final ArrayList<Course>[] table2;

	@SuppressWarnings("unchecked")
	private Scheduler(
			EntityManagerFactory emf,
			Program program,
			List<TimeSlot> timeslots,
			List<Room> rooms,
			List<Course> courses,
			Set<Person> lecturers) {

		table1 = (ArrayList<Course>[]) new ArrayList<?>
				[timeslots.size() * rooms.size()];
		table2 = (ArrayList<Course>[]) new ArrayList<?>
				[timeslots.size() * lecturers.size()];

		int[] allocations = new int[timeslots.size() * rooms.size()];

		for (int i = 0; i < allocations.length; i++) {
			allocations[i] = i;
		}
		Random random = new Random();
		for (int i = 0; i < allocations.length; i++) {
			int x = random.nextInt(allocations.length);
			int y = random.nextInt(allocations.length);
			int tmp = allocations[x];
			allocations[x] = allocations[y];
			allocations[y] = tmp;
		}

		int i = 0;
		for (Course course : courses) {
			int p = allocations[i++];
			table1[p] = new ArrayList<Course>();
			table1[p].add(course);
		}
	}

	/**
	 * Create a Scheduler initialized with a little test data set.
	 * 
	 * @return The Scheduler.
	 */
	public static Scheduler sampleScheduler() {
		List<TimeSlot> timeslots = new ArrayList<TimeSlot>(40);
		List<Room> rooms = new ArrayList<Room>();
		List<CourseUnit> courses = new ArrayList<CourseUnit>();

		Room r;
		Building b = new Building("taku9");

		r = new Room(b, "GroßerHS");
		b.getRooms().add(r);

		r = new Room(b, "005", 200);
		b.getRooms().add(r);

		r = new Room(b, "005");
		b.getRooms().add(r);

		r = new Room(b, "046");
		b.getRooms().add(r);

		r = new Room(b, "049");
		b.getRooms().add(r);

		r = new Room(b, "051");
		b.getRooms().add(r);

		r = new Room(b, "053");
		b.getRooms().add(r);

		r = new Room(b, "K40");
		b.getRooms().add(r);

		return null;
	}

	/**
	 * Create a Scheduler that schedules the specified program, using the
	 * specified EntityManagerFactory.
	 * 
	 * @param emf
	 *            The EntityManagerFactory used to create EntityManagers.
	 * @param programId
	 *            The id of the program which is to be scheduled.
	 * @return Either the Scheduler or a String explaining why the Scheduler
	 *         could not be created (i.e. if some checks like “enough rooms for
	 *         all the courses” failed).
	 */
	public static Either<Scheduler, String> schedule(EntityManagerFactory emf,
			long programId) {
		EntityManager em = EntityManagerWrapper.wrap(emf.createEntityManager());
		EntityTransaction transaction = em.getTransaction();
		try {

			Program program = em.find(Program.class, programId);
			if (program == null) {
				return ValueUtil.right("");
			}

			List<TimeSlot> timeslots = em.findAll(TimeSlot.class);
			List<Room> rooms = em.findAll(Room.class);
			List<Course> courses = program.getCourses();
			Set<Person> lecturers = new TreeSet<Person>();
			for (Course course : courses) {
				if (course.getLecturer() != null) {
					lecturers.add(course.getLecturer());
				}
				for (Person lecturer : course.getPossibleLecturers()) {
					lecturers.add(lecturer);
				}
			}

			program.setStatus(Program.Status.SCHEDULING);
			em.persist(program);

			transaction.commit();
			em.close();

			return ValueUtil.left(new Scheduler(emf, program,
					timeslots, rooms, courses, lecturers));
		} catch (Exception exc) {
			return ValueUtil.right("");
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	class A {
		private void m() {
		}
	}

	class B extends A {
		public void m() {
		}
	}
}