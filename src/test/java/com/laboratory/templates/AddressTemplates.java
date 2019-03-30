package com.laboratory.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.laboratory.model.Address;

public class AddressTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Address.class).addTemplate("VALID", new Rule() {{
            add("street", "Passagem vila nova");
            add("neighborhood", "Sacramenta");
            add("streetNumber", "56");
            add("postalCode", "66123120");
        }});
    }
}
