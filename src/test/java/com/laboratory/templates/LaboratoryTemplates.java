package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.model.Address;
import com.laboratory.model.Laboratory;

public class LaboratoryTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Laboratory.class).addTemplate("VALID", new Rule() {{
            add("name", "Delboni");
            add("address", one(Address.class, "VALID"));
        }});

        Fixture.of(Laboratory.class).addTemplate("VALID-2", new Rule() {{
            add("name", "Lavoisier");
            add("address", one(Address.class, "VALID"));
        }});

        Fixture.of(Laboratory.class).addTemplate("VALID-PUT", new Rule() {{
            add("name", "Delboni-PUT");
            add("address", one(Address.class, "VALID"));
        }});
    }
}
