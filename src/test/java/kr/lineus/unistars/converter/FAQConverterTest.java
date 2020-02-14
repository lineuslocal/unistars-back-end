package kr.lineus.unistars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import kr.lineus.unistars.dto.FAQ;
import kr.lineus.unistars.dto.FAQCategory;
import kr.lineus.unistars.dto.FAQProduct;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.dto.UserLevel;
import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.FAQCategoryEntity;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQProductEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;
import kr.lineus.unistars.entity.UserEntity;

public class FAQConverterTest {

	@Test
	public void whenConvertFAQSubjectEntityToFAQSubject_thenCorrect() {
		
		UUID uuid = UUID.randomUUID();
		FAQSubjectEntity en = new FAQSubjectEntity();
		en.setId(uuid);
		en.setName("subject1");
		
		
		FAQCategoryEntity catEn = new FAQCategoryEntity();
		catEn.setId(UUID.randomUUID());
		catEn.setName("cat1");
		en.addFAQCategoryEntity(catEn);
		
		FAQProductEntity prodEn = new FAQProductEntity();
		prodEn.setId(UUID.randomUUID());
		prodEn.setName("prod1");
		catEn.addFAQProductEntity(prodEn);
		
		FAQEntity faqEn = new FAQEntity();
		faqEn.setId(UUID.randomUUID());
		faqEn.setContent("This is a dummy FAQ");
		faqEn.setCreatedDate(LocalDate.now());
		faqEn.setLevel(UserLevel.Advanced);
		faqEn.setTitle("title1");
		faqEn.setProduct(prodEn);
		prodEn.addFAQEntity(faqEn);
		
		faqEn = new FAQEntity();
		faqEn.setId(UUID.randomUUID());
		faqEn.setContent("This is a dummy FAQ");
		faqEn.setCreatedDate(LocalDate.now());
		faqEn.setLevel(UserLevel.Advanced);
		faqEn.setTitle("title2");
		faqEn.setProduct(prodEn);
		prodEn.addFAQEntity(faqEn);
		
		
		FAQSubject sub = FAQConverter.getInstance().faqSubjectEntityToDto(en);
		assertEquals(sub.getId(), en.getId().toString());
		assertEquals(sub.getName(), en.getName().toString());
		
		List<FAQCategory> cats = sub.getCategories();
		assertEquals(cats.size(), 1);
		assertEquals(cats.get(0).getId(), catEn.getId().toString());
		assertEquals(cats.get(0).getName(), catEn.getName());
		
		List<FAQProduct> prods = cats.get(0).getProducts();
		assertEquals(prods.size(), 1);
		assertEquals(prods.get(0).getId(), catEn.getProducts().get(0).getId().toString());
		assertEquals(prods.get(0).getName(), catEn.getProducts().get(0).getName());
		
		List<FAQ> faqs = cats.get(0).getProducts().get(0).getFaqs();
		for(int i=0; i<faqs.size(); i++) {
			assertEquals(faqs.get(i).getId(), prodEn.getFaqs().get(i).getId().toString());
			assertEquals(faqs.get(i).getContent(), prodEn.getFaqs().get(i).getContent());
			assertEquals(faqs.get(i).getTitle(), prodEn.getFaqs().get(i).getTitle());
			assertEquals(faqs.get(i).getLevel(), prodEn.getFaqs().get(i).getLevel());
			assertEquals(faqs.get(i).getCreatedDate(), prodEn.getFaqs().get(i).getCreatedDate());
			
		}
	}
	
	
	@Test
	public void whenConvertFAQSubjectToFAQSubjectEntity_thenCorrect() {
		
		
		UUID uuid = UUID.randomUUID();
		FAQSubject faqSubject = new FAQSubject();
		faqSubject.setId(uuid.toString());
		faqSubject.setName("subject1");
		
		
		FAQCategory faqCat = new FAQCategory();
		faqCat.setId(UUID.randomUUID().toString());
		faqCat.setName("cat1");
		faqSubject.getCategories().add(faqCat);
		
		FAQProduct prodEn = new FAQProduct();
		prodEn.setId(UUID.randomUUID().toString());
		prodEn.setName("prod1");
		faqCat.getProducts().add(prodEn);
		
		FAQ faq = new FAQ();
		faq.setId(UUID.randomUUID().toString());
		faq.setContent("This is a dummy FAQ");
		faq.setCreatedDate(LocalDate.now());
		faq.setLevel(UserLevel.Advanced);
		faq.setTitle("title1");
		prodEn.getFaqs().add(faq);
		
		faq = new FAQ();
		faq.setId(UUID.randomUUID().toString());
		faq.setContent("This is a dummy FAQ");
		faq.setCreatedDate(LocalDate.now());
		faq.setLevel(UserLevel.Advanced);
		faq.setTitle("title2");
		prodEn.getFaqs().add(faq);
		
		
		FAQSubjectEntity sub = FAQConverter.getInstance().faqSubjectDtoToEntity(faqSubject);
		assertEquals(sub.getId().toString(), faqSubject.getId().toString());
		assertEquals(sub.getName(), faqSubject.getName().toString());
		
		
		List<FAQCategoryEntity> catEns = sub.getCategories();
		assertEquals(catEns.size(), 1);
		assertEquals(catEns.get(0).getId().toString(), faqCat.getId());
		assertEquals(catEns.get(0).getName(), faqCat.getName());
		
		List<FAQProductEntity> prods = catEns.get(0).getProducts();
		assertEquals(prods.size(), 1);
		assertEquals(prods.get(0).getId().toString(), prodEn.getId().toString());
		assertEquals(prods.get(0).getName(), prodEn.getName());
		
		List<FAQEntity> faqs = prods.get(0).getFaqs();
		for(int i=0; i<faqs.size(); i++) {
			assertEquals(faqs.get(i).getId().toString(), prodEn.getFaqs().get(i).getId().toString());
			assertEquals(faqs.get(i).getContent(), prodEn.getFaqs().get(i).getContent());
			assertEquals(faqs.get(i).getTitle(), prodEn.getFaqs().get(i).getTitle());
			assertEquals(faqs.get(i).getLevel(), prodEn.getFaqs().get(i).getLevel());
			assertEquals(faqs.get(i).getCreatedDate(), prodEn.getFaqs().get(i).getCreatedDate());
			
		}
	}
	
	
	
	
	
}
