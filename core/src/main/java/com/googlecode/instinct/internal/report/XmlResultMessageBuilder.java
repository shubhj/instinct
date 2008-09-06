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

package com.googlecode.instinct.internal.report;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.report.ResultMessageBuilderException;
import fj.Effect;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public final class XmlResultMessageBuilder implements ResultMessageBuilder {
    private static final String TEMPLATE_FILE_NAME = "context_result_report.vm";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final Object DECIMAL_FORMAT = new DecimalFormat("0.000");
    @SuppressWarnings({"StaticNonFinalField"}) private static ResultMessageBuilderException initialisationException;

    static {
        try {
            Velocity.addProperty("resource.loader", "class");
            Velocity.addProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
            Velocity.init();
        } catch (Exception e) {
            initialisationException = new ResultMessageBuilderException("Could not initialise " + XmlResultMessageBuilder.class.getName(), e);
        }
    }

    public String buildMessage(final ContextClass contextClass) {
        checkNotNull(contextClass);
        return "";
    }

    @SuppressWarnings({"OverlyBroadCatchBlock"})
    public String buildMessage(final ContextResult result) throws ResultMessageBuilderException {
        if (initialisationException != null) {
            throw initialisationException;
        }
        checkNotNull(result);
        final Writer w = new StringWriter();
        final Context context = createPopulatedContext(result);
        try {
            Velocity.mergeTemplate(TEMPLATE_FILE_NAME, "UTF-8", context, w);
        } catch (Exception e) {
            throw new ResultMessageBuilderException("Could not merge template with results data", e);
        }
        return w.toString();
    }

    public String buildMessage(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        return "";
    }

    private Context createPopulatedContext(final ContextResult result) {
        final Context context = new VelocityContext();
        context.put("result", result);
        context.put("hostname", getHostname());
        context.put("reportdate", DATE_FORMAT.format(new Date()));
        context.put("decimal_format", DECIMAL_FORMAT);
        final Collection<SpecificationResult> resultsAsJavaList = new ArrayList<SpecificationResult>(result.getSpecificationResults().length());
        result.getSpecificationResults().foreach(new Effect<SpecificationResult>() {
            public void e(final SpecificationResult result) {
                resultsAsJavaList.add(result);
                result.getStatus();
            }
        });
        context.put("spec_results", resultsAsJavaList);
        context.put("properties", System.getProperties());
        return context;
    }

    public String buildMessage(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        return "";
    }

    public String buildMessage(final ContextResultsSummary summary) {
        checkNotNull(summary);
        return "";
    }

    private Object getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
