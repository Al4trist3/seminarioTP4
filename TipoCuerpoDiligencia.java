import java.util.Calendar;

//Interfaz para implementar mediante el patron Strategy los diferentes algoritmos para generar el cuerpo de una diligencia de acuerdo a su tipo
interface TipoCuerpoDiligencia {
    String generarTextoCuerpo(Diligencia diligencia);
    String nombreTipo();

    default String obtenerMes(int mes) {
        String[] nombresMeses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        
        return nombresMeses[mes];

    }
}

class Declaracion implements TipoCuerpoDiligencia {
    @Override
    public String generarTextoCuerpo(Diligencia diligencia) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(diligencia.getFecha());

        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        String texto = 
            "-///- la Ciudad Autonoma de Buenos Aires, Capital Federal de la República Argentina, hoy día " + dia + " de "
            + this.obtenerMes(mes) + "del año " + anio + ", comparece ante la prevención una persona que ratificó llamarse "
            + diligencia.getInstructor().getNombre() + ". Acto seguido DECLARA: " + diligencia.getTextoCuerpo()
            + "Es cuanto declara. Terminado el acto y leída la presente, se ratifico de todo su contenido, firmando por ante mí para constancia de que CERTIFICO.-";

        return texto;
    }

    @Override
    public String nombreTipo() {
        return "Declaracion";
    }
}

class Informe implements TipoCuerpoDiligencia {
    @Override
    public String generarTextoCuerpo(Diligencia diligencia) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(diligencia.getFecha());

        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        String texto = 
            "-///- la Ciudad Autonoma de Buenos Aires, Capital Federal de la Nación Argentina hoy día "
            + dia + " de " + this.obtenerMes(mes) + " del año "
            + anio +", la Instrucción a los efectos legales hace CONSTAR: " 
            + diligencia.getTextoCuerpo() + " CONSTE.-";
    
        return texto;
    }

    @Override
    public String nombreTipo() {
        return "Informe";
    }
}

class Constancia implements TipoCuerpoDiligencia {
    @Override
    public String generarTextoCuerpo(Diligencia diligencia) {
        Sumario sumario = diligencia.sumario();
        String texto =
            "Tengo el agrado de dirigirme a usted en el marco de actuaciones incoadas en este Departamento registradas bajo causa numero "
            + sumario.getNumero() + " en trámite por ante el Juzgado " + sumario.getJuzgado() + ", del Dr. " + sumario.getJuez()
            + " y la Fiscalía " + sumario.getFiscalia() + ", del Dr. " + sumario.getFiscal() + diligencia.getTextoCuerpo();

        return texto;
                
    }

    @Override
    public String nombreTipo() {
        return "Constancia";
    }
}