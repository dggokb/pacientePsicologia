package br.com.diego.psicologia.comum;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public final class DateUtils {

    private static final int QUANTIDADE_DE_DIAS_NA_SEMANA = 7;

    private static final int CURVA_LIMITE_DE_ANOS_PERMITIDA = 10;

    private static final String FORMATO_DE_DATA_DD_MM_YYYY = "dd/MM/yyyy";

    private static final String FORMATO_DE_DATA_DD_MM_YYYY_HORA_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    private static final String FORMATO_DE_HORAS_HH_MM_SS = "hh:mm:ss";

    private static final String FORMADO_DE_HORAS_HH_MM = "HH:mm";

    private static final int ULTIMO_DIA_DA_SEMANA = 6;

    private static final int UM_DIA = 1;

    private DateUtils() {
    }

    public static boolean aDataEstaNessePeriodo(Date periodoInicial, Date periodoFinal, Date dataParaVerificar) {
        LocalDate dataInicial = converteDateToLocalDate(zerarHoraDoAtributoData(periodoInicial));
        LocalDate dataFinal = converteDateToLocalDate(zerarHoraDoAtributoData(periodoFinal));
        LocalDate dataDaVerificacao = converteDateToLocalDate(zerarHoraDoAtributoData(dataParaVerificar));
        return (dataDaVerificacao.compareTo(dataInicial) >= 0) && (dataDaVerificacao.compareTo(dataFinal) <= 0);
    }

    public static boolean aDataNaoEstaNessePeriodo(Date periodoInicial, Date periodoFinal, Date dataParaVerificar) {
        return !aDataEstaNessePeriodo(periodoInicial, periodoFinal, dataParaVerificar);
    }

    public static boolean isDataFutura(Date data) {
        return isDataFutura(data, Calendar.getInstance().getTime());
    }

    public static boolean isDataAnterior(Date data) {
        return isDataAnterior(data, Calendar.getInstance().getTime());
    }

    public static boolean isDataIgual(Date primeiraData, Date segundaData) {
        LocalDateTime primeiraDataSemHora = converteDateToLocalDate(primeiraData).atStartOfDay();
        LocalDateTime segundaDataSemHora = converteDateToLocalDate(segundaData).atStartOfDay();
        return primeiraDataSemHora.equals(segundaDataSemHora);
    }

    public static boolean isDiaDaSemanaIgual(Date data1, Date data2) {
        Calendar calendar1 = converterDataParaCalendar(data1);
        Calendar calendar2 = converterDataParaCalendar(data2);
        return calendar1.get(Calendar.DAY_OF_WEEK) == calendar2.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isDataFimDeSemana(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    public static Date alterarDataParaDiaDeSemana(Date data) {
        while (isDataFimDeSemana(data)) {
            data = adicionarOuSubtrairDiasNaData(data, UM_DIA);
        }
        return data;
    }

    public static LocalDate alterarDataParaDiaDeSemana(LocalDate data) {
        Date dataConvertida = converterLocalDateToDate(data);

        while (isDataFimDeSemana(dataConvertida)) {
            dataConvertida = adicionarOuSubtrairDiasNaData(dataConvertida, UM_DIA);
        }
        return converteDateToLocalDate(dataConvertida);
    }

    public static boolean isDataFutura(Date dataParaValidar, Date dataDeReferencia) {
        LocalDate data = converteDateToLocalDate(dataParaValidar);
        LocalDate referencia = converteDateToLocalDate(dataDeReferencia);
        return data.isAfter(referencia);
    }

    public static boolean isDataAnterior(Date dataParaValidar, Date dataDeReferencia) {
        LocalDate data = converteDateToLocalDate(dataParaValidar);
        LocalDate referencia = converteDateToLocalDate(dataDeReferencia);
        return data.isBefore(referencia);
    }

    public static boolean verificaSeDataComHoraEhFutura(Date dataParaValidar, Date dataDeReferencia) {
        LocalDateTime data = converteDateToLocalDateTime(dataParaValidar);
        LocalDateTime referencia = converteDateToLocalDateTime(dataDeReferencia);
        return data.isAfter(referencia);
    }

    public static boolean verificaSeDataComHoraEhAnterior(Date dataParaValidar, Date dataDeReferencia) {
        LocalDateTime data = converteDateToLocalDateTime(dataParaValidar);
        LocalDateTime referencia = converteDateToLocalDateTime(dataDeReferencia);
        return data.isBefore(referencia);
    }

    public static boolean isAnoIgual(Date dataParaValidar, Date referencia) {
        Calendar calendarioDataParaValidar = converterDataParaCalendar(dataParaValidar);
        Calendar calendarioReferencia = converterDataParaCalendar(referencia);
        return calendarioDataParaValidar.get(Calendar.YEAR) == calendarioReferencia.get(Calendar.YEAR);
    }

    public static boolean isDataIgualOuFutura(Date data1, Date data2) {
        return isDataFutura(data1, data2) || isDataIgual(data1, data2);
    }

    public static Calendar converterDataParaCalendar(Date data) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return calendar;
    }

    public static XMLGregorianCalendar converteDateParaXMLGregorianCalendar(Date data) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(data);
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {
            xmlGregorianCalendar =
                    DatatypeFactory.newInstance().newXMLGregorianCalendarDate(gregorianCalendar.get(Calendar.YEAR),
                            gregorianCalendar.get(Calendar.MONTH) + 1,
                            gregorianCalendar.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
        }
        return xmlGregorianCalendar;
    }

    public static Date zerarHoraDoAtributoData(Date data) {
        return adicionarHoraNaData(data, 0, 0, 0);
    }

    public static Date setarDataParaUmaHora(Date data) {
        return adicionarHoraNaData(data, 1, 0, 0);
    }

    public static Date adicionarHoraNaData(Date data, int horas, int minutos, int segundos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), horas,
                minutos, segundos);
        return calendar.getTime();
    }

    public static Date adicionarMinutosNaData(Date data, Integer minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    public static String obterDataFormatada(Date data) {
        return data != null ? new SimpleDateFormat("dd/MM/yyyy").format(data) : "";
    }

    public static String obterDataFormatada(LocalDate data) {
        Date dataConvertida = converterLocalDateToDate(data);
        return dataConvertida != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataConvertida) : "";
    }

    public static String obterDataComHoraFormatada(LocalDateTime dataComHora) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dataComHora);
    }

    public static String obterMesPorExtenso(Date data) {
        Locale.setDefault(new Locale("pt", "BR"));
        DateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        return dateFormat.format(data);
    }

    public static String obterMesPorExtenso(LocalDate data) {
        return obterMesPorExtenso(converterLocalDateParaDate(data));
    }

    public static String obterMesPorExtenso(LocalDateTime data) {
        return obterMesPorExtenso(data.toLocalDate());
    }

    private static Date converterLocalDateParaDate(LocalDate data) {
        return Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Integer getAno(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        return calendar.get(Calendar.YEAR);
    }

    public static String getMes(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        return String.valueOf(calendar.get(Calendar.MONTH));
    }

    public static String getDia(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        return String.valueOf(calendar.get(Calendar.DATE));
    }

    public static int getDiaDaSemana(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static Integer getMinuto(Date data) {
        Calendar calendar = converterDataParaCalendar(data);
        return calendar.get(Calendar.MINUTE);
    }

    public static String getDataPorExtenso(Date data) {
        return getDia(data) + " de " + obterMesPorExtenso(data) + " de " + getAno(data);
    }

    public static String getDataPorExtenso(LocalDate data) {
        return getDataPorExtenso(converterLocalDateParaDate(data));
    }

    public static String getDataAtualPorExtenso() {
        return getDataPorExtenso(new Date());
    }

    public static String toString(Date data) {
        return data != null ? new SimpleDateFormat(FORMATO_DE_DATA_DD_MM_YYYY).format(data) : "";
    }

    public static List<Date> buscarTodosOsDiasDaSemana(Date dia) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dia);

        int domingo = 1 - calendario.get(GregorianCalendar.DAY_OF_WEEK);
        calendario.add(Calendar.DAY_OF_MONTH, domingo);

        List<Date> diasDaSemana = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_DE_DIAS_NA_SEMANA; i++) {
            diasDaSemana.add(calendario.getTime());
            calendario.add(Calendar.DAY_OF_MONTH, 1);
        }
        return diasDaSemana;
    }

    public static Date adicionarOuSubtrairDiasNaData(Date data, int diasASeremAdicionadosOuSubtraidos) {
        Calendar calendar = converterDataParaCalendar(data);
        calendar.add(Calendar.DATE, diasASeremAdicionadosOuSubtraidos);
        return calendar.getTime();
    }

    public static Date subtrairDiasNaData(Date data, int diasASeremSubtraidos) {
        Calendar calendar = converterDataParaCalendar(data);
        diasASeremSubtraidos = diasASeremSubtraidos < 0 ? diasASeremSubtraidos : diasASeremSubtraidos * -1;
        calendar.add(Calendar.DATE, diasASeremSubtraidos);
        return calendar.getTime();
    }

    public static Date criarData(String data) {
        try {
            return data.contains(":") ? new SimpleDateFormat(FORMATO_DE_DATA_DD_MM_YYYY_HORA_HH_MM_SS).parse(data) :
                    new SimpleDateFormat(FORMATO_DE_DATA_DD_MM_YYYY).parse(data);
        } catch (ParseException e) {
            throw new IllegalArgumentException("O formato de dada é inválido.");
        }
    }

    public static LocalDate criarLocalDate(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        } catch (Exception exception) {
            throw new ExcecaoDeRegraDeNegocio("Data inválida. É necessário informar a data no formato dd/mm/aaaa.");
        }
    }

    public static LocalDateTime criarLocalDateTime(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return converterLocalDateParaLocalDateTime(LocalDate.parse(data, formatter));
    }

    public static LocalDateTime converterLocalDateParaLocalDateTime(LocalDate data) {
        return LocalDateTime.of(data, LocalTime.parse("00:00:00"));
    }

    public static Date converterDataDoBanco(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(data);
        } catch (ParseException e) {
            throw new ExcecaoDeRegraDeNegocio("Erro ao converter a data " + data + " do banco de dados.");
        }
    }

    public static LocalDateTime converterLocalDateTimeDaApi(String dataDaApi) {
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return LocalDateTime.parse(dataDaApi, formatador);
        } catch (DateTimeParseException excecaoDeConversaoDeData) {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
            return LocalDateTime.parse(dataDaApi, formatador);
        }
    }

    public static Date buscarDataDeSabadoDaSemana(Date data) {
        return buscarTodosOsDiasDaSemana(data).get(ULTIMO_DIA_DA_SEMANA);
    }

    public static boolean isAnoDiferente(Date data, Integer ano) {
        return !isAnoIgual(data, ano);
    }

    public static boolean isAnoDiferente(Date data1, Date data2) {
        return !isAnoIgual(data1, data2);
    }

    public static boolean isAnoIgual(Date data, Integer ano) {
        Calendar calendarioDataParaValidar = converterDataParaCalendar(data);
        return calendarioDataParaValidar.get(Calendar.YEAR) == ano;
    }

    public static Date converterLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate converteDateToLocalDate(Date data) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(data.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime converteDateToLocalDateTime(Date data) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(data.getTime()), ZoneId.systemDefault());
    }

    public static Date converterLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Integer quantidadeDeDiasEntreAsDatas(Date dataInicial, Date dataFinal) {
        LocalDateTime localDateInicial = converteDateToLocalDateTime(dataInicial);
        LocalDateTime localDateFinal = converteDateToLocalDateTime(dataFinal);

        return Math.toIntExact(DAYS.between(localDateInicial, localDateFinal)) + 1;
    }

    public static Integer obterAnoPassado(Integer anoAtual) {
        return anoAtual - 1;
    }

    public static Date getDataAtual() {
        return new Date();
    }

    public static Integer getAnoAtual() {
        return getAno(getDataAtual());
    }

    public static Boolean isHoraInicialEhMenorOuIgualQueHoraFinal(String horaInicial, String horaFinal) {
        LocalTime localTimeHoraInicial = converterStringToLocalTime(horaInicial);
        LocalTime localTimeHoraFinal = converterStringToLocalTime(horaFinal);
        return horaInicialEhMenorOuIgualAHoraFinal(localTimeHoraInicial, localTimeHoraFinal);
    }

    public static Boolean horaInicialEhMenorOuIgualAHoraFinal(LocalTime localTimeHoraInicial,
                                                              LocalTime localTimeHoraFinal) {
        return localTimeHoraInicial.compareTo(localTimeHoraFinal) == 0 || localTimeHoraInicial.compareTo(localTimeHoraFinal) == -1;
    }

    public static LocalDate converterStringToLocalDate(String data) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATO_DE_DATA_DD_MM_YYYY);
        return LocalDate.parse(data, dateTimeFormatter);
    }

    public static LocalTime converterStringToLocalTime(String hora) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMADO_DE_HORAS_HH_MM);
        return LocalTime.parse(hora, dateTimeFormatter);
    }

    public static String converterLocalTimeEmString(LocalTime hora) {
        return hora.format(DateTimeFormatter.ofPattern(FORMADO_DE_HORAS_HH_MM));
    }

    public static String obterHoraFormatada(String hora) {
        hora = hora.replace(":", "h:");
        hora = hora.concat("min");
        return hora;
    }

    public static String obterHoraFormatada(Date data) {
        SimpleDateFormat formata = new SimpleDateFormat(FORMATO_DE_HORAS_HH_MM_SS);
        return formata.format(data);
    }

    public static boolean aDataEstaForaDoLimitePermitido(Date dataForaDoLimite) {
        LocalDate localDate = LocalDate.now();
        Integer anoAtual = localDate.getYear();
        Integer anoDaDataForaDoLimite = getAno(dataForaDoLimite);

        Integer anoMaximoPermitidoPeloSistema = anoAtual + CURVA_LIMITE_DE_ANOS_PERMITIDA;
        Integer anoMinimoPermitidoPeloSistema = anoAtual - CURVA_LIMITE_DE_ANOS_PERMITIDA;
        return anoDaDataForaDoLimite > anoMaximoPermitidoPeloSistema ||
                anoDaDataForaDoLimite < anoMinimoPermitidoPeloSistema &&
                        aQuantidadeDeCaracteresNaoCorrespondeAUmAno(anoDaDataForaDoLimite);
    }

    private static boolean aQuantidadeDeCaracteresNaoCorrespondeAUmAno(Integer anoDaDataForaDoLimite) {
        return !(anoDaDataForaDoLimite.toString().length() == 4);
    }

    public static Date obterDataComFormato(String data, String formato) throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        return formatador.parse(data);
    }

    public static LocalDateTime alterarDataParaProximoDiaUtilQuandoFinalDeSemana(LocalDateTime dataDoFinalDeSemana) {
        if (dataEhFinalDeSemana(dataDoFinalDeSemana)) {
            LocalDateTime data = dataDoFinalDeSemana.plusDays(1);
            if (dataEhFinalDeSemana(data)) {
                return data.plusDays(1);
            }
            return data;
        }
        return dataDoFinalDeSemana;
    }

    public static String obterDataFormatada(Date data, String formato) {
        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        return formatador.format(data);

    }


    public static List<Date> obterDatasEntreUmPeriodo(Date dataInicial, Date dataFinal) {
        List<Date> diasDoPeriodo = new ArrayList<>();
        do {
            diasDoPeriodo.add(dataInicial);
            dataInicial = adicionarOuSubtrairDiasNaData(dataInicial, UM_DIA);
        } while (dataInicial.compareTo(dataFinal) <= 0);
        return diasDoPeriodo;
    }

    public static boolean dataEhFinalDeSemana(LocalDateTime data) {
        return data.getDayOfWeek().equals(DayOfWeek.SATURDAY) || data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public static boolean dataEhFinalDeSemana(LocalDate data) {
        return data.getDayOfWeek().equals(DayOfWeek.SATURDAY) || data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public static LocalDate alterarDataParaProximoDiaUtilQuandoFinalDeSemana(LocalDate dataDoFinalDeSemana) {
        if (dataEhFinalDeSemana(dataDoFinalDeSemana)) {
            LocalDate data = dataDoFinalDeSemana.plusDays(1);
            if (dataEhFinalDeSemana(data)) {
                return data.plusDays(1);
            }
            return data;
        }
        return dataDoFinalDeSemana;
    }

    public static boolean verificarSeEhUmaDataValida(String data) {
        if (data == null) {
            return false;
        }

        try {
            SimpleDateFormat formatador = data.contains(":")
                    ? new SimpleDateFormat(FORMATO_DE_DATA_DD_MM_YYYY_HORA_HH_MM_SS)
                    : new SimpleDateFormat(FORMATO_DE_DATA_DD_MM_YYYY);
            formatador.setLenient(false);
            formatador.parse(data);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean verificarSeEhUmaHoraValida(String hora) {
        if (hora == null) {
            return false;
        }

        try {
            boolean horaEstaComSegundos = hora.replaceAll("^[\\d]+", "").equals("::");
            SimpleDateFormat formatador = horaEstaComSegundos
                    ? new SimpleDateFormat(FORMATO_DE_HORAS_HH_MM_SS)
                    : new SimpleDateFormat(FORMADO_DE_HORAS_HH_MM);
            formatador.setLenient(false);
            formatador.parse(hora);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static LocalDateTime converterStringDeDataEmSQLParaLocalDateTime(String data) {
        return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSS"));
    }

    public static List<LocalDate> obterDatasDeSegundaASabado(LocalDate dia) {
        LocalDate diaDaSemana = dia.with(DayOfWeek.MONDAY);
        List<LocalDate> diasDaSemana = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            diasDaSemana.add(diaDaSemana);
            diaDaSemana = diaDaSemana.plusDays(1);
        }
        return diasDaSemana;
    }

    public static LocalDate obterSegundaDaProximaSemana(LocalDate dia) {
        return dia.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }


    public static boolean verificarSeDiasDaSemanaSaoIguais(LocalDate primeiraData,
                                                           LocalDate segundaData) {
        return primeiraData.getDayOfWeek().equals(segundaData.getDayOfWeek());
    }

    public static LocalDate obterPrimeiroDiaDaSemana(LocalDate data) {
        return data.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    public static LocalDate obterPrimeiroDiaDaSemana(Date data) {
        LocalDate dataConvertida = DateUtils.converteDateToLocalDate(data);
        return obterPrimeiroDiaDaSemana(dataConvertida);
    }

    public static LocalDate obterUltimoDiaDaSemana(LocalDate data) {
        return data.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
    }

    public static LocalDate obterUltimoDiaDaSemana(Date data) {
        LocalDate dataConvertida = DateUtils.converteDateToLocalDate(data);
        return obterUltimoDiaDaSemana(dataConvertida);
    }

    public static LocalDate obterPrimeiroDiaDoMes(LocalDate data) {
        return data.withDayOfMonth(1);
    }

    public static LocalDate obterPrimeiroDiaDoMes(Date data) {
        LocalDate dataConvertida = DateUtils.converteDateToLocalDate(data);
        return obterPrimeiroDiaDoMes(dataConvertida);
    }

    public static LocalDate obterUltimoDiaDoMes(LocalDate data) {
        return data.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate obterUltimoDiaDoMes(Date data) {
        LocalDate dataConvertida = DateUtils.converteDateToLocalDate(data);
        return obterUltimoDiaDoMes(dataConvertida);
    }

    public static int obterQuantidadeDeDiasEntre(LocalDate dataInicial, LocalDate dataFinal) {
        return (int) dataInicial.until(dataFinal, DAYS);
    }

    public static int obterQuantidadeDeDiasEntre(String dataInicial, String dataFinal) {
        return (int) DateUtils.criarLocalDate(dataInicial).until(DateUtils.criarLocalDate(dataFinal), DAYS);
    }

    public static String obterHorarioPorExtenso(LocalDateTime data) {
        String descricaoDasHoras = data.getHour() == 1 ? "hora" : "horas";
        String descricaoDosMinutos = data.getMinute() == 1 ? "minuto" : "minutos";
        return String.format("%s %s e %s %s.", data.getHour(), descricaoDasHoras, data.getMinute(), descricaoDosMinutos);
    }
}