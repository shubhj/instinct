/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.report.html.ContextResultSummary;
import com.googlecode.instinct.internal.report.html.ContextResultSummaryFactory;
import com.googlecode.instinct.internal.report.html.ContextResultSummaryFactoryImpl;
import com.googlecode.instinct.internal.report.html.ReportWriterImpl;
import com.googlecode.instinct.internal.report.html.SpecificationReport;
import com.googlecode.instinct.internal.report.html.UnparseableContentException;
import fj.Effect;
import fj.F;
import fj.data.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.JarResourceLoader;

@SuppressWarnings({"StaticNonFinalField", "OverlyBroadCatchBlock"})
public final class InstinctReportAntTask extends Task {
    private static final ContextResultSummaryFactory SUMMARY_FACTORY = new ContextResultSummaryFactoryImpl();
    private static Exception initialisationException;
    private List<FileSet> filesets = List.nil();
    private File file;

    static {
        try {
            final URL resource = InstinctReportAntTask.class.getResource("/" + ReportWriterImpl.INSTINCT_SPEC_REPORT_TEMPLATE);
            final String url = resource.getFile();
            final String file = "jar:" + (url.contains("!") ? url.substring(0, url.indexOf("!")) : url);
            Velocity.addProperty("resource.loader", "jar");
            Velocity.addProperty("jar.resource.loader.class", JarResourceLoader.class.getName());
            Velocity.addProperty("jar.resource.loader.path", file);
            Velocity.init();
        } catch (Exception e) {
            initialisationException = e;
        }
    }

    @Override
    public void execute() throws BuildException {
        if (initialisationException != null) {
            throw new BuildException("Could not initialise " + InstinctReportAntTask.class, initialisationException);
        }
        final List<FileResource> files = collateFiles();
        final SpecificationReport report = new SpecificationReport();
        log("Generating Instinct report on " + files.length() + " files");
        files.foreach(new Effect<FileResource>() {
            public void e(final FileResource file) {
                final ContextResultSummary summary;
                try {
                    summary = SUMMARY_FACTORY.createFrom(file);
                } catch (UnparseableContentException e) {
                    throw new BuildException(e);
                }
                report.add(summary);
            }
        });
        final String s = new ReportWriterImpl().write(report);
        log("Writing report to " + file.getAbsolutePath());
        writeToFile(s);
    }

    @SuppressWarnings({"CallToPrintStackTrace"})
    private void writeToFile(final String s) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log(e, Project.MSG_ERR);
            }
        }
    }

    public void addFileset(final FileSet fileset) {
        filesets = filesets.snoc(fileset);
    }

    public void setFile(final File file) {
        this.file = file;
    }

    private List<FileResource> collateFiles() {
        file.getAbsolutePath();
        return filesets.foldLeft(new F<List<FileResource>, F<FileSet, List<FileResource>>>() {
            public F<FileSet, List<FileResource>> f(final List<FileResource> list) {
                return new F<FileSet, List<FileResource>>() {
                    @SuppressWarnings({"RawUseOfParameterizedType"})
                    public List<FileResource> f(final FileSet set) {
                        List<FileResource> files = List.nil();
                        final Iterator iter = set.iterator();
                        while (iter.hasNext()) {
                            files = files.snoc((FileResource) iter.next());
                        }
                        return files;
                    }
                };
            }
        }, List.<FileResource>nil());
    }
}
