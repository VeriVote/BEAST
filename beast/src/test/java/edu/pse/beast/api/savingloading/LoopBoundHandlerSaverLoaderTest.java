package edu.pse.beast.api.savingloading;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.Test;

import edu.pse.beast.api.codegen.loopbounds.FunctionAlreadyContainsLoopboundAtIndexException;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.savingloading.loopbound.LoopBoundHandlerSaverLoaderHelper;

public class LoopBoundHandlerSaverLoaderTest {

	@Test
	public void testLoopBoundSaverLoader() throws FunctionAlreadyContainsLoopboundAtIndexException {
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		for (int i = 0; i < 20; ++i) {
			loopBoundHandler.pushLoopBound("main",
					LoopBoundType.LOOP_BOUND_AMT_CANDS);
		}

		for (int i = 20; i < 40; ++i) {
			loopBoundHandler.addLoopBoundForFunction("main", i,
					LoopBoundType.MANUALLY_ENTERED_INTEGER, Optional.of(i));

		}

		JSONObject json = LoopBoundHandlerSaverLoaderHelper
				.loopboundHandlerToJson(loopBoundHandler);

		LoopBoundHandler loadedLoopBoundHandler = LoopBoundHandlerSaverLoaderHelper
				.loopBoundHandlerFromJSON(json);

		for (String name : loadedLoopBoundHandler.getFuncNameToLoopbounds()
				.keySet()) {
			assertTrue(loopBoundHandler.getFuncNameToLoopbounds()
					.containsKey(name));
		}

		for (String name : loadedLoopBoundHandler.getFuncNameToLoopbounds()
				.keySet()) {
			List<LoopBound> l1 = loadedLoopBoundHandler
					.getFuncNameToLoopbounds().get(name);
			List<LoopBound> l2 = loopBoundHandler.getFuncNameToLoopbounds()
					.get(name);

			assertEquals(l1.size(), l2.size());

			for (int i = 0; i < l1.size(); ++i) {
				assertEquals(l1.get(i).getFunctionName(),
						l2.get(i).getFunctionName());
				assertEquals(l1.get(i).getIndex(), l2.get(i).getIndex());
				assertEquals(l1.get(i).getLoopBoundType(),
						l2.get(i).getLoopBoundType());
				if (l1.get(i)
						.getLoopBoundType() == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
					assertEquals(l1.get(i).getManualLoopBoundIfPresent().get(),
							l1.get(i).getManualLoopBoundIfPresent().get());
				}
			}
		}

	}
}