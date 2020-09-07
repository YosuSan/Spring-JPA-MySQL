package com.bolsaideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfWiev extends AbstractPdfView {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Factura factura = (Factura) model.get("factura");

		Locale locale = localeResolver.resolveLocale(request);

		/* Con esta forma no hace falta locale hereda de MessageSource, lo maneja todo por debajo*/
		MessageSourceAccessor message = getMessageSourceAccessor();

		PdfPCell cell = null;

		PdfPTable tabla1 = new PdfPTable(1);
		tabla1.setSpacingAfter(20);

		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.customerData", null, locale)));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidthBottom(2);
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);

		tabla1.addCell(cell);
//		tabla1.addCell("Datos del cliente");
		tabla1.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla1.addCell(factura.getCliente().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);

		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.billData", null, locale)));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidthBottom(2);
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);

		tabla2.addCell(cell);
//		tabla2.addCell("Datos de la factura");
		tabla2.addCell(message.getMessage("text.billData.number") + ": " + factura.getId());
		tabla2.addCell(message.getMessage("text.billData.details") + ": " + factura.getDescripcion());
		tabla2.addCell(message.getMessage("text.billData.date") + ": " + factura.getCreateAt());

		PdfPTable tabla3 = new PdfPTable(4);
		/* Establece el ancho de la celda producto como *2.5 en relacion a las otras */
		tabla3.setWidths(new float[] { 2.5f, 1, 1, 1 });

		tabla3.addCell(messageSource.getMessage("text.items.product", null, locale));
		tabla3.addCell(cellAlignRight(messageSource.getMessage("text.items.price", null, locale)));
		tabla3.addCell(cellAlignRight(messageSource.getMessage("text.items.quantity", null, locale)));
		tabla3.addCell(cellAlignRight(messageSource.getMessage("text.items.total", null, locale)));

		for (ItemFactura item : factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(cellAlignRight(item.getProducto().getPrecio().toString()));
			tabla3.addCell(cellAlignRight(item.getCantidad().toString()));
			tabla3.addCell(cellAlignRight(item.calcularImporte().toString()));
		}

		cell = cellAlignRight(messageSource.getMessage("text.items.total", null, locale) + ": ");
		cell.setBorderWidthTop(2);
		cell.setColspan(3);
		cell.setPadding(6f);
		tabla3.addCell(cell);
		cell = cellAlignRight(factura.getTotal().toString());
		cell.setBorderWidthTop(2);
		cell.setPadding(6f);
		tabla3.addCell(cell);

		document.add(tabla1);
		document.add(tabla2);
		document.add(tabla3);
	}

	private PdfPCell cellAlignRight(String str) {
		PdfPCell cell = new PdfPCell(new Phrase(str));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		return cell;
	}

}
