import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void testAddInvalidCommands() {
		String invalidCommand = "creat checking 12345678 1.0";
		commandStorage.addInvalidCommands(invalidCommand);

		List<String> allCommands = commandStorage.getInvalidCommands();

		assertTrue(allCommands.contains(invalidCommand));

	}

	@Test
	void testAddMultipleInvalidCommands() {
		String invalid1 = "creat checking 12345678 1.0";
		String invalid2 = "creat savings 12345678 1.0";

		commandStorage.addInvalidCommands(invalid1);
		commandStorage.addInvalidCommands(invalid2);

		List<String> allCommands = commandStorage.getInvalidCommands();

		assertTrue(allCommands.contains(invalid1));
		assertTrue(allCommands.contains(invalid2));
	}

	@Test
	void testGetCommandsForEmptyList() {
		List<String> allCommands = commandStorage.getInvalidCommands();
		assertEquals(0, allCommands.size());
	}
}
