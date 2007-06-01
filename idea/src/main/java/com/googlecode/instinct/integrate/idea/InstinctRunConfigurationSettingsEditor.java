/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.integrate.idea;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import com.googlecode.instinct.internal.util.Fix;
import com.intellij.execution.junit2.configuration.ClassBrowser;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"MethodParameterOfConcreteClass", "LocalVariableOfConcreteClass"})
public final class InstinctRunConfigurationSettingsEditor extends SettingsEditor<InstinctRunConfiguration> {
    private Project project;
    private TextFieldWithBrowseButton contextClassInput;
    private JComboBox moduleComponent;
    private JComponent editorUi;

    public InstinctRunConfigurationSettingsEditor(final Project project) {
        this.project = project;
        initComponents();
    }

    @Override
    public void resetEditorFrom(final InstinctRunConfiguration s) {
        contextClassInput.setText(s.getContextClassName());
        moduleComponent.setSelectedItem(s.getModule());
    }

    @Override
    public void applyEditorTo(final InstinctRunConfiguration s) {
        s.setContextClassName(contextClassInput.getText());
        s.setModule((Module) moduleComponent.getSelectedItem());
    }

    @NotNull
    @Override
    public JComponent createEditor() {
        return editorUi;
    }

    @Override
    public void disposeEditor() {
    }

    private void initComponents() {
        initClassChooser();
        initModuleComboBox();
        editorUi = createEditorUi();
    }

    private void initClassChooser() {
        contextClassInput = new TextFieldWithBrowseButton();
        contextClassInput.addActionListener(new ActionListener() {
            @Fix("Make sure the browse box is not already showing.")
            public void actionPerformed(final ActionEvent e) {
                final ContextClassBrowser browser = new ContextClassBrowser(project, "Choose Context");
                browser.setField(contextClassInput);
                contextClassInput.setText(browser.show());
                // reset the name of the context in the "Name" field.
            }
        });
    }

    private void initModuleComboBox() {
        moduleComponent = new JComboBox(ModuleManager.getInstance(project).getSortedModules());
        moduleComponent.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
                    final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    final Module module = (Module) value;
                    label.setText(module.getName());
                    label.setIcon(module.getModuleType().getNodeIcon(false));
                }
                return label;
            }
        });
        moduleComponent.setSelectedIndex(0);
    }

    private JComponent createEditorUi() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(contextClassInput, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(4, 4, 0, 4), 0, 0
        ));
        panel.add(new Label("Use classpath and JDK of module:"), new GridBagConstraints(
                0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 4, 0, 4), 0, 0
        ));
        panel.add(moduleComponent, new GridBagConstraints(
                0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 4, 0, 4), 0, 0
        ));
        panel.add(new JPanel(), new GridBagConstraints(
                0, 5, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        ));
        return panel;
    }

    private static class ContextClassBrowser extends ClassBrowser {
        private ContextClassBrowser(final Project project, final String name) {
            super(project, name);
        }

        @Override
        protected TreeClassChooser.ClassFilterWithScope getFilter() throws NoFilterException {
            return new TreeClassChooser.ClassFilterWithScope() {
                public GlobalSearchScope getScope() {
                    return GlobalSearchScope.projectTestScope(getProject(), false);
                }

                public boolean isAccepted(final PsiClass aClass) {
                    return ClassUtil.isContextClass(aClass);
                }
            };
        }

        @Override
        protected PsiClass findClass(final String name) {
            return null;
        }

        public String show() {
            return super.showDialog();
        }
    }
}
