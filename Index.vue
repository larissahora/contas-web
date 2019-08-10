<template >
  <v-app>
    <div>
      <v-toolbar>
        <v-toolbar-title>Olá</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-toolbar-items>
          <v-btn text v-for="mes in meses" :key="mes.id" @click="pegarMes(mes.id)">{{mes.name}}</v-btn>
        </v-toolbar-items>

        <template v-if="$vuetify.breakpoint.smAndUp">
          <v-btn icon @click="mostrarForm">
            <v-icon>mdi-plus-circle</v-icon>
          </v-btn>
        </template>
      </v-toolbar>
    </div>
    <v-flex xs10 offset-xs4 id="bemvindo">
      Seja bem-vindo(a)!
      <br />Clique no mês para adicionar uma conta ou ver o saldo do mês.
    </v-flex>
    <v-layout row justify-center space-around>
      <v-flex xs4 offset-xs1>
        <form id="form" style="display: none">
          <v-text-field v-model="descricao" label="Descrição" data-vv-name="descricao" required></v-text-field>
          <v-text-field v-model="valor" label="Valor" data-vv-name="valor" required type="number"></v-text-field>
          <v-checkbox
            v-model="checkbox"
            value="1"
            label="Fixa"
            data-vv-name="checkbox"
            type="checkbox"
            required
          ></v-checkbox>
          <v-radio-group v-model="tipo" row>
            <v-radio label="Despesa" value="1"></v-radio>
            <v-radio label="Receita" value="0"></v-radio>
          </v-radio-group>

          <v-select v-model="select" :items="items" label="Parcelas" data-vv-name="select" outlined></v-select>

          <v-btn class="mr-4" @click="cadastrarConta">Cadastrar conta</v-btn>
        </form>
      </v-flex>
    </v-layout>

    <v-layout column align-center style="display:none" id="saldo">
      <v-flex md1 d-flex color="primary">
        <v-card dark :color="getColor(total)">
          <v-card-text class="px-0">Saldo: {{this.total}}</v-card-text>
        </v-card>
      </v-flex>
    </v-layout>

    <v-layout row style="display:none" id="conta">
      <v-flex xs10 offset-xs1>
        <v-data-table :headers="headers" :items="porMes" class="elevation-1">
          <template v-slot:item.valor="{ item }">
            <v-chip :color="getColor(item.tipo)" dark>{{ item.valor }}</v-chip>
            <v-btn icon @click="mostrarFormEdit">
              <v-icon>fa fa-edit</v-icon>
            </v-btn>
            <v-btn v-if="item.tipo == '1'" icon @click="pagarConta(item.id)">
              <v-icon>far fa-money-bill-alt</v-icon>
            </v-btn>
            <form style="display:none" id="form2">
              <v-container grid-list-xl>
                <v-layout wrap>
                  <v-flex xs12 md4>
                    <v-text-field :value="item.descricao" id="descricao" label="Descrição"></v-text-field>
                  </v-flex>

                  <v-flex xs12 md4>
                    <v-text-field :value="item.valor" label="Valor" id="valor" type="number"></v-text-field>
                  </v-flex>
                  <v-btn class="mr-4" @click="editarConta(item.id)">Editar conta</v-btn>
                </v-layout>
              </v-container>
            </form>
          </template>
        </v-data-table>
      </v-flex>
    </v-layout>
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      items: [
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "8",
        "9",
        "10",
        "11",
        "12"
      ],
      headers: [
        {
          text: "",
          align: "left",
          sortable: false,
          value: ""
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        },
        { text: "", value: "" },
        { text: "", value: "" },
        { text: "", value: "" },
        { text: "Valor", value: "valor" }
      ],
      meses: [
        { id: "1", name: "Janeiro" },
        { id: "2", name: "Fevereiro" },
        { id: "3", name: "Março" },
        { id: "4", name: "Abril" },
        { id: "5", name: "Maio" },
        { id: "6", name: "Junho" },
        { id: "7", name: "Julho" },
        { id: "8", name: "Agosto" },
        { id: "9", name: "Setembro" },
        { id: "10", name: "Outubro" },
        { id: "11", name: "Novembro" },
        { id: "12", name: "Dezembro" }
      ],
      contas: [],
      checkbox: 0,
      tipo: 0,
      valor: null,
      select: null,
      descricao: null,
      cadastroConta: {},
      mes: null,
      ano: 2019,
      form: null,
      contasList: [],
      sum: 0,
      sub: 0,
      total: 0,
      maisContas: [],
      descricaoEditar: null,
      valorEditar: null
    };
  },
  mounted() {},
  created() {},
  methods: {
      //metodo que cadastra conta 
      //depende se é fixa ou parcelada
      // fixa cadastra +12 vezes
      //parcelada cadastra o numero de parcelas vezes
    cadastrarConta() {
      $("#form").css("display", "none");
      $("#saldo").css("display", "block");

      this.cadastroConta = {
        descricao: this.descricao,
        valor: this.valor,
        ano: this.ano,
        mes: this.mes,
        fixa: this.checkbox,
        tipo: this.tipo,
        parcelas: this.select
      };

      if (this.checkbox == "1") {
        this.select = 1;
        for (let i = 0; i <= 12; i++) {
          this.cadastroConta = {
            descricao: this.descricao,
            valor: this.valor,
            ano: this.ano,
            mes: this.mes++,
            fixa: this.checkbox,
            tipo: this.tipo,
            parcelas: this.select
          };
          axios.post("http://localhost:9000/cadastrar", this.cadastroConta);
          this.mostrarConta();
        }
      }

      console.log(this.select);

      if (this.select > 1) {
        this.valor = this.valor / this.select;
        var arredondado = parseFloat(this.valor.toFixed(2));
        for (let i = 0; i < this.select; i++) {
          this.cadastroConta = {
            descricao: this.descricao,
            valor: arredondado,
            ano: this.ano,
            mes: this.mes++,
            fixa: this.checkbox,
            tipo: this.tipo,
            parcelas: this.select
          };

          axios.post("http://localhost:9000/cadastrar", this.cadastroConta);
          this.mostrarConta();
        }
      } else {
        axios.post("http://localhost:9000/cadastrar", this.cadastroConta);
        this.mostrarConta();
      }
      this.descricao = "";
      this.valor = "";
      this.checkbox = 0;
      this.fixa = 0;
      this.select = "";
    },
    //metodo para atualizar as contas 
    mostrarConta() {
      axios
        .get("http://localhost:9000/conta")
        .then(response => {
          this.contasList = response.data;
          //somatório dos valores das contas por mês
          for (let i = 0; i < this.contasList.length; i++) {
            console.log(this.mes);
            if (this.maisContas.length == "1") {
              this.result = this.maisContas[i].valor;
              this.total = parseFloat(this.result.toFixed(2));
            } else {
              if (this.contasList[i].mes == this.mes) {
                if (this.contasList[i].tipo == 1) {
                  this.sub += this.contasList[i].valor;
                  var arredondado1 = parseFloat(this.sub.toFixed(2));
                } else {
                  this.sum += this.contasList[i].valor;
                  var arredondado2 = parseFloat(this.sum.toFixed(2));
                }
                this.total = arredondado2 - arredondado1;
              }
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    //ao pagar a conta, a conta é deletada da lista
    pagarConta(id) {
      axios.delete("http://localhost:9000/pagar/" + id);
    },
    //metodo para mudar a cor do valor, se for despesa, fica vermelho
    //se for receita, fica verde
    getColor(tipo, saldo) {
      if (tipo == "1") return "red";
      else return "green";
      if (saldo < 0) return "red";
      else return "green";
    },
    //metodo para pegar o id do mês que foi clicado no nav
    //atualiza a lista de contas
    pegarMes(id) {
      $("#conta").css("display", "block");
      $("#saldo").css("display", "block");
      $("#bemvindo").css("display", "none");

      this.mes = id;

      axios
        .get("http://localhost:9000/conta")
        .then(response => {
          this.maisContas = response.data;

          for (let i = 0; i < this.maisContas.length; i++) {
            if (this.maisContas.length == "1") {
              this.result = this.maisContas[i].valor;
              this.total = parseFloat(this.result.toFixed(2));
            } else {
              if (this.maisContas[i].mes == this.mes) {
                console.log("mes: " + this.mes);
                console.log("mes do banco: " + this.maisContas[i].mes);
                if (this.maisContas[i].tipo == 1) {
                  this.sub += this.maisContas[i].valor;
                  var arredondado1 = parseFloat(this.sub.toFixed(2));
                  console.log("subtração: " + arredondado1);
                } else {
                  this.sum += this.maisContas[i].valor;
                  var arredondado2 = parseFloat(this.sum.toFixed(2));
                  console.log("soma: " + arredondado2);
                }
                this.result = arredondado2 - arredondado1;
                this.total = parseFloat(this.result.toFixed(2));
                console.log("total: " + this.total);
              }
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.sub = 0;
      this.sum = 0;
      this.total = 0;
      var arredondado1 = 0;
      var arredondado2 = 0;
      this.result = 0;
    },
    //metodo para mostrar o form de cadastrar conta ao clicar no botão +
    //e para esconder o saldo
    mostrarForm() {
      $("#form").css("display", "block");
      $("#saldo").css("display", "none");
    },
    //metodo para editar uma conta
    editarConta(id) {
      $("#form2").css("display", "none");
      this.descricao = $("#descricao").val();
      this.valor = $("#valor").val();
      console.log(id);
      this.id = id;
      this.cadastroConta = {
        id: this.id,
        descricao: this.descricao,
        valor: this.valor,
        ano: this.ano,
        mes: this.mes,
        fixa: this.fixa,
        tipo: this.tipo,
        parcelas: this.select
      };
      console.log($("#descricao").val());
      console.log($("#idConta").val());
      axios.put("http://localhost:9000/atualizar", this.cadastroConta);
    },
    //metodo para mostrar o form de editar conta
    mostrarFormEdit() {
      $("#form2").css("display", "block");
    }
  },
  computed: {
      //filtro para mostrar as contas do mês selecionado no nav
    porMes() {
      axios
        .get("http://localhost:9000/conta")
        .then(response => {
          this.contas = response.data;
        })
        .catch(error => {
          console.log(error);
        });

      return this.contas.filter(res => {
        if (res.mes == this.mes) return true;
        else return false;
      });
    }
  },
  //metodo para observar e atualizar instanteneamente as contas
  watch: {
    verMudanca() {
      this.mostrarConta();
    }
  }
};
</script>
