package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.dto.LaboratoryDto;
import com.laboratory.model.Address;

public class LaboratoryDtoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(LaboratoryDto.class).addTemplate("VALID", new Rule() {{
            add("name", "Flour Laboratory");
            add("address", one(Address.class, "VALID"));
        }});
    }
}
