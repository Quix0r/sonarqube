/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.application.process;

import java.io.File;
import java.util.Properties;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.process.ProcessId;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractCommandTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void test_command_with_complete_information() throws Exception {
    AbstractCommand command = new AbstractCommand(ProcessId.ELASTICSEARCH) {

    };

    command.setArgument("first_arg", "val1");
    Properties args = new Properties();
    args.setProperty("second_arg", "val2");
    command.setArguments(args);

    command.setEnvVariable("JAVA_COMMAND_TEST", "1000");
    File workDir = temp.newFolder();
    command.setWorkDir(workDir);

    assertThat(command.toString()).isNotNull();
    assertThat(command.getWorkDir()).isSameAs(workDir);

    // copy current env variables
    assertThat(command.getEnvVariables().get("JAVA_COMMAND_TEST")).isEqualTo("1000");
    assertThat(command.getEnvVariables().size()).isEqualTo(System.getenv().size() + 1);
  }

}
