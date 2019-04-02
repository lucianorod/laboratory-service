package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.dto.ExamDto;

public class ExamDtoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(ExamDto.class).addTemplate("VALID", new Rule() {{
            add("name", "ULTRASSONOGRAFIA");
            add("examType", "IMAGEM");
        }});

        Fixture.of(ExamDto.class).addTemplate("VALID-PUT", new Rule() {{
            add("name", "ULTRASSONOGRAFIA");
            add("examType", "ANALISE CLINICA");
        }});
    }
}
