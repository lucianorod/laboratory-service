package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.model.Exam;

public class ExamTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Exam.class).addTemplate("VALID", new Rule() {{
            add("name", "ULTRASSONOGRAFIA");
            add("examType", "IMAGEM");
        }});

        Fixture.of(Exam.class).addTemplate("VALID-2", new Rule() {{
            add("name", "MAMOGRAFIA");
            add("examType", "IMAGEM");
        }});
    }
}
