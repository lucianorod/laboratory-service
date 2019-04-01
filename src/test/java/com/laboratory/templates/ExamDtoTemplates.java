package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.dto.ExamInDto;

import java.util.Collections;

public class ExamDtoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(ExamInDto.class).addTemplate("VALID", new Rule() {{
            add("name", "ULTRASSONOGRAFIA");
            add("examType", "IMAGEM");
            add("laboratories", Collections.singleton(Long.valueOf(1)));
        }});

        Fixture.of(ExamInDto.class).addTemplate("VALID-PUT", new Rule() {{
            add("name", "ULTRASSONOGRAFIA");
            add("examType", "ANALISE CLINICA");
            add("laboratories", Collections.singleton(Long.valueOf(1)));
        }});
    }
}
